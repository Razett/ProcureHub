package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> list() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orders.forEach(x->orderDTOList.add(orderEntityToDTO(x)));
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
        if(opOr.isPresent()){
            return orderEntityToDTO(opOr.get());
        }
        else{
            return null;
        }
    }

    @Override
    public List<OrderDTO> totalList() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> totalList = new ArrayList<>();
        orders.forEach(x->totalList.add(orderEntityToDTO(x)));
        return totalList;
    }
}
