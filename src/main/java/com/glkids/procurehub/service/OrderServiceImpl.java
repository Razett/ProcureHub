package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.*;
import com.glkids.procurehub.status.ImportStatus;
import com.glkids.procurehub.status.InspectionStatus;
import com.glkids.procurehub.status.OrderStatus;
import com.glkids.procurehub.status.PrcrStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProcurementService procurementService;
    private final OrderRepository orderRepository;
    private final QuotationMtrlRepository quotationMtrlRepository;
    private final MaterialRepository materialRepository;
    private final OrderInspectionRepository orderInspectionRepository;
    private final ImportRepository importRepository;

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
        BooleanExpression statusExp = qOrder.status.gt(OrderStatus.MODIFIED.ordinal());

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
    public Order read(Long orderno) {
        Optional<Order> opOr = orderRepository.findById(orderno);
        return opOr.get();
    }

    @Override
    public List<OrderDTO> totalList() {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC,"orderno"));
        List<OrderDTO> totalList = new ArrayList<>();
        orders.forEach(x -> totalList.add(orderEntityToDTO(x)));
        return totalList;
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
}
