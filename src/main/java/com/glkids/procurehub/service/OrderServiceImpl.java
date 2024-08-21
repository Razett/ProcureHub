package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.entity.QOrder;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.repository.OrderRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.status.OrderStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final QuotationMtrlRepository quotationMtrlRepository;

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
    public void register(OrderDTO orderDTO) {
        orderRepository.save(orderDtoToEntity(orderDTO));
    }

    @Override
    public OrderDTO read(Long orderno) {
        Optional<Order> opOr = orderRepository.findById(orderno);
        return opOr.map(this::orderEntityToDTO).orElse(null);
    }

    @Override
    public List<OrderDTO> totalList() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> totalList = new ArrayList<>();
        orders.forEach(x -> totalList.add(orderEntityToDTO(x)));
        return totalList;
    }

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

                if (updatedOrder.getOrderdate().equals(order.getOrderdate())) {
                    executeList.add(orderEntityToDTO(order));
                }
            }
        }
        return executeList;
    }
}
