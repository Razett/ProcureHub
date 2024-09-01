package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.dto.OrderInspectionDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.*;
import com.glkids.procurehub.status.ImportStatus;
import com.glkids.procurehub.status.InspectionStatus;
import com.glkids.procurehub.status.OrderStatus;
import com.glkids.procurehub.status.PrcrStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProcurementService procurementService;
    private final OrderRepository orderRepository;
    private final QuotationMtrlRepository quotationMtrlRepository;
    private final MaterialRepository materialRepository;
    private final OrderInspectionRepository orderInspectionRepository;
    private final ImportRepository importRepository;
    private final PrcrRepository prcrRepository;

    @Override
    public List<OrderDTO> getOrderListBefore() {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        QOrder qOrder = QOrder.order;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression statusExp = qOrder.status.lt(OrderStatus.CONTINUING.ordinal());

        orderRepository.findAll(builder.and(statusExp)).forEach(x -> {
            List<QuotationMtrl> list = quotationMtrlRepository.findByMaterial(x.getMaterial().getMtrlno());
            OrderDTO orderDTO = orderEntityToDTO(x);
            orderDTO.setQuotationmtrlList(list);
            orderDTOList.add(orderDTO);
        });
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getOrderListAfter() {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        QOrder qOrder = QOrder.order;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression statusExp = qOrder.status.gt(OrderStatus.MODIFIED.ordinal()).and(qOrder.status.lt(OrderStatus.OK.ordinal()));

        orderRepository.findAll(builder.and(statusExp)).forEach(x -> orderDTOList.add(orderEntityToDTO(x)));
        return orderDTOList;
    }

    @Transactional
    @Override
    public void update(List<OrderDTO> orderDTOList) {
        for (OrderDTO orderDTO : orderDTOList) {
            QuotationMtrl qm = QuotationMtrl.builder().qtmtno(orderDTO.getQtmtno()).build();
            orderRepository.updateOrder(qm, orderDTO.getQuantity(), OrderStatus.MODIFIED.ordinal(), orderDTO.getOrderno());
        }
    }

    @Transactional
    @Override
    public void delete(Long orderno) {
        orderRepository.orderDelete(orderno);
    }

    @Override
    public boolean register(OrderDTO orderDTO) {
        Order order = new Order();

        // Material이 이미 존재하는지 확인
        Material material = materialRepository.findById(orderDTO.getMaterial().getMtrlno())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        order.setMaterial(material);
        order.setEmp(orderDTO.getEmp());
        order.setOrderdate(orderDTO.getOrderdate());
        order.setQuantity(orderDTO.getQuantity());
        order.setTrackingNo(orderDTO.getTrackingNo());
        order.setStatus(orderDTO.getStatus());

        // 이제 Order를 저장
        orderRepository.save(order);

        return orderRepository.existsById(order.getOrderno());
    }

    @Override
    public OrderDTO read(Long orderno) {
        Optional<Order> opOr = orderRepository.findById(orderno);
        if (opOr.isPresent()) {
            Order order = opOr.get();
            QuotationMtrl qtmt = order.getQuotationmtrl();
            OrderDTO dto = OrderDTO.builder().orderno(order.getOrderno())
                    .emp(order.getEmp())
                    .orderdate(order.getOrderdate())
                    .quantity(order.getQuantity())
                    .material(order.getMaterial())
                    .status(order.getStatus())
                    .regdate(order.getRegdate())
                    .moddate(order.getModdate()).build();

            if (qtmt != null) {
                dto.setQuotationmtrl(QuotationMtrl.builder().qtmtno(qtmt.getQtmtno()).unitprice(qtmt.getUnitprice()).leadtime(qtmt.getLeadtime()).build());
                dto.setContractorName(qtmt.getQuotation().getContractor().getName());

            }

            if (order.getPrcr() != null) {
                dto.setPrcr(Prcr.builder().prcrno(order.getPrcr().getPrcrno()).reqdate(order.getPrcr().getReqdate()).build());
            }
            return dto;
        }
        return null;
    }

    @Override
    public List<OrderInspectionDTO> inspectionRead(Long orderno) {
        List<OrderInspectionDTO> list = new ArrayList<>();

        orderInspectionRepository.readOrderInspectionByOrderno(orderno).forEach(ins -> {
            OrderInspectionDTO dto = OrderInspectionDTO.builder()
                    .nspcno(ins.getNspcno())
                    .duedate(ins.getDuedate())
                    .status(ins.getStatus())
                    .content(ins.getContent())
                    .build();
            if (ins.getInspector() != null) {
                dto.setInspector(ins.getInspector());
            }
            list.add(dto);
        });

        return list;
    }

    @Transactional
    @Override
    public Boolean inspectionRegister(OrderInspectionDTO orderInspectionDTO, Emp emp) {
        String date = orderInspectionDTO.getDuedateString();

        LocalDateTime duedate = parseDate(date);
        orderInspectionDTO.setDuedate(duedate);

        Long orderno = orderInspectionDTO.getOrderno();
        orderInspectionDTO.setOrder(Order.builder().orderno(orderno).build());

        if (orderInspectionDTO.getStatus() != 0) {
            orderInspectionDTO.setInspector(emp);
        }

        OrderInspection ins = orderInspectionRepository.save(orderInspectionDTOToEntity(orderInspectionDTO));
        orderRepository.changeStatus(orderno, OrderStatus.INSPECTING.ordinal());

        return ins.getNspcno() != null;
    }

    @Deprecated
    @Override
    public List<OrderDTO> totalList() {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC,"orderno"));
        List<OrderDTO> totalList = new ArrayList<>();
        orders.forEach(x -> totalList.add(orderEntityToDTO(x)));
        return totalList;
    }

    @Override
    public List<OrderDTO> totalList(String type, String input) {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        orderRepository.findOrdersByStatus(OrderStatus.INSPECTING.ordinal()).forEach(order -> {
            orderDTOList.add(orderEntityToDTO(order));
        });
        return orderDTOList;
    }

    @Transactional
    @Override
    public List<OrderDTO> orderExecute(List<OrderDTO> orderDTOList, Emp emp) {
        List<OrderDTO> executeList = new ArrayList<>();

        for (OrderDTO orderDTO : orderDTOList) {
            Optional<Order> orderOptional = orderRepository.findById(orderDTO.getOrderno());
            QuotationMtrl quotationMtrl = quotationMtrlRepository.findById(orderDTO.getQtmtno()).orElse(null);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();

                order.setEmp(emp);
                order.setOrderdate(LocalDateTime.now());
                order.setQuotationmtrl(quotationMtrl);
                order.setStatus(OrderStatus.NEEDS_INSPECTION.ordinal());

                Order updatedOrder = orderRepository.save(order);

                // 입고 검수 건 기본 한 개 추가.
                OrderInspection orderInspection = OrderInspection.builder()
                        .order(order)
                        .status(InspectionStatus.NOT_YET.ordinal())
                        .duedate(LocalDateTime.now().plusDays(1)).build();

                orderInspectionRepository.save(orderInspection);

                // 입고 대기 항목 추가
                Imports imports = Imports.builder()
                        .quantity(order.getQuantity())
                        .status(ImportStatus.AUTO_GENERATED.ordinal())
                        .order(order).build();

                importRepository.save(imports);

                if (order.getPrcr() != null) {
                    procurementService.changeStatus(order.getPrcr().getPrcrno(), PrcrStatus.ORDERED);
                }

                if (updatedOrder.getOrderdate().equals(order.getOrderdate())) {
                    executeList.add(orderEntityToDTO(order));
                }
            }
        }

        return executeList;
    }

    @Override
    public void changeStatus(Long orderno, OrderStatus orderStatus) {
        orderRepository.changeStatus(orderno, orderStatus.ordinal());
    }

    @Transactional
    public long countRedStatus() {
        return prcrRepository.countByStatusIn(Arrays.asList(
                PrcrStatus.RED_ORDER_ADDED.ordinal()
        ));
    }

    // 경고 상태 (YELLOW 및 YELLOW_ORDER_ADDED)의 prcr 개수 반환
    @Transactional
    public long countYellowStatus() {
        return orderRepository.countByStatusIn(Arrays.asList(
                OrderStatus.AUTO_GENERATED.ordinal()
        ));
    }

    // 경고 상태 (YELLOW 및 YELLOW_ORDER_ADDED)의 prcr 개수 반환
    @Transactional
    public long countBlueStatus() {
        return orderRepository.countByStatusIn(Arrays.asList(
                OrderStatus.AUTO_MODIFIED.ordinal()
        ));
    }

    // 전체 조달계획의 개수 반환
    @Transactional
    public long countTotalOrder() {
        return orderRepository.count();
    }

    public LocalDateTime parseDate(String dateStr) {
        // 날짜 문자열의 포맷 정의
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-M-d"); // 예: "2024-8-5"
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 예: "2024-12-11"

        LocalDate date = null;

        // 날짜 문자열을 LocalDate로 변환
        try {
            date = LocalDate.parse(dateStr, formatter1);
        } catch (DateTimeParseException e1) {
            try {
                date = LocalDate.parse(dateStr, formatter2);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Invalid date format: " + dateStr);
            }
        }

        // LocalDate를 LocalDateTime으로 변환 (시간 부분을 기본값인 00:00:00으로 설정)
        return date.atStartOfDay();
    }
}
