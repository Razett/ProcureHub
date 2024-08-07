package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.entity.QOrder;
import com.glkids.procurehub.repository.EmpRepository;
import com.glkids.procurehub.repository.OrderRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EmpRepository empRepository;

    @Override
    public List<OrderDTO> getOrderListBefore() {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        QOrder qOrder = QOrder.order;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression statusExp = qOrder.status.eq(0);

        orderRepository.findAll(builder.and(statusExp)).forEach(x -> orderDTOList.add(orderEntityToDTO(x)));
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getOrderListAfter() {
        List<OrderDTO> orderDTOList = new ArrayList<>();

        QOrder qOrder = QOrder.order;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression statusExp = qOrder.status.eq(1);

        orderRepository.findAll(builder.and(statusExp)).forEach(x -> orderDTOList.add(orderEntityToDTO(x)));
        return orderDTOList;
    }

    @Override
    public void register(OrderDTO orderDTO) {
        Order orEntity = orderDtoToEntity(orderDTO);
        orderRepository.save(orEntity);
    }

    @Override
    public void update(OrderDTO orderDTO) {
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
    public List<OrderDTO> orderExecute(List<Long> ordernos) {
        List<OrderDTO> executeList = new ArrayList<>();

        Emp emp = Emp.builder().empno(201758030L).build();

        for (Long orderno : ordernos) {
            Optional<Order> orderOptional = orderRepository.findById(orderno);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();

                order.setEmp(emp);
                order.setOrderdate(LocalDateTime.now());
                order.setStatus(1);

                Order updatedOrder = orderRepository.save(order);

                if (updatedOrder.getOrderdate().equals(order.getOrderdate())) {
                    executeList.add(orderEntityToDTO(order));
                }
            }
        }

        return executeList;
    }
}
