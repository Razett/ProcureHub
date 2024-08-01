package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Order;

import java.util.List;

public interface OrderService {

    //1. 발주 현황 목록
    List<OrderDTO> list();

    //2. 발주 수동 추가
    void register(OrderDTO orderDTO);

    //3. 발주 진행 수정
    void update(OrderDTO orderDTO);

    //4. 발주 상세 정보
    OrderDTO read(Long orderno);

    //5. 발주 전체 내역 목록
    List<OrderDTO> totalList();

    default Order orderDtoToEntity(OrderDTO orderDTO) {
        Order order=Order.builder().orderno(orderDTO.getOrderno()).emp(orderDTO.getEmp())
                .orderdate(orderDTO.getOrderdate()).prcr(orderDTO.getPrcr()).quotation(orderDTO.getQuotation())
                .quantity(orderDTO.getQuantity()).trackingNo(orderDTO.getTrackingNo()).status(orderDTO.getStatus())
                .build();

        return order;
    }

    default OrderDTO orderEntityToDTO(Order entity) {
        return OrderDTO.builder().orderno(entity.getOrderno()).emp(entity.getEmp()).orderdate(entity.getOrderdate())
                .prcr(entity.getPrcr()).quotation(entity.getQuotation()).quantity(entity.getQuantity())
                .trackingNo(entity.getTrackingNo()).status(entity.getStatus()).build();

    }
}
