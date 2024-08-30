package com.glkids.procurehub.service;


import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.dto.OrderInspectionDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.status.OrderStatus;
import com.glkids.procurehub.status.QuotationStatus;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    //1. 발주 현황 목록
    //1-1. 발주 실행 전 대기 현황
    List<OrderDTO> getOrderListBefore();

    //1-2. 발주 실행 후 진행 현황
    List<OrderDTO> getOrderListAfter();

    //1-3. 대기 현황 수정
    void update(List<OrderDTO> orderDTOList);

    //1-4. 발주 취소(삭제)
    void delete(Long orderno);

    //2. 발주 수동 추가
    boolean register(OrderDTO orderDTO);

    //4. 발주 상세 정보
    OrderDTO read(Long orderno);

    //5-1. 발주 진척 검수 조회
    List<OrderInspectionDTO> inspectionRead(Long orderno);

    //5-2. 발주 진척 검수 추가
    Boolean inspectionRegister(OrderInspectionDTO orderInspectionDTO, Emp emp);

    //6. 발주 전체 내역 목록
    @Deprecated
    List<OrderDTO> totalList();

    //6-1. 발주 전체 내역 목록
    List<OrderDTO> totalList(String type, String input);

    //7.발주 실행
    List<OrderDTO> orderExecute(List<OrderDTO> orderDTOList, Emp emp);

    void changeStatus(Long orderno, OrderStatus orderStatus);

    default Order orderDtoToEntity(OrderDTO orderDTO) {
        Order order = Order.builder().orderno(orderDTO.getOrderno()).emp(orderDTO.getEmp())
                .orderdate(orderDTO.getOrderdate()).prcr(orderDTO.getPrcr()).material(orderDTO.getMaterial()).quotationmtrl(orderDTO.getQuotationmtrl())
                .quantity(orderDTO.getQuantity()).trackingNo(orderDTO.getTrackingNo()).status(orderDTO.getStatus())
                .build();

        return order;
    }

    default OrderDTO orderEntityToDTO(Order entity) {
        QuotationMtrl quotationmtrl = entity.getQuotationmtrl();
        return OrderDTO.builder()
                .orderno(entity.getOrderno())
                .emp(entity.getEmp())
                .orderdate(entity.getOrderdate())
                .prcr(entity.getPrcr())
                .material(entity.getMaterial())
                .quotationmtrl(entity.getQuotationmtrl())
                .quantity(entity.getQuantity())
                .trackingNo(entity.getTrackingNo())
                .status(entity.getStatus())
                .regdate(entity.getRegdate())
                .moddate(entity.getModdate())
                .build();

    }

    default OrderInspection orderInspectionDTOToEntity(OrderInspectionDTO orderInspectionDTO){
        OrderInspection orderInspection = OrderInspection.builder().nspcno(orderInspectionDTO.getNspcno()).order(orderInspectionDTO.getOrder())
                .duedate(orderInspectionDTO.getDuedate()).content(orderInspectionDTO.getContent()).inspector(orderInspectionDTO.getInspector())
                .status(orderInspectionDTO.getStatus()).build();

        return  orderInspection;
    }

    default OrderInspectionDTO orderInspectionEntityToDTO(OrderInspection entity){
        return  OrderInspectionDTO.builder()
                .nspcno(entity.getNspcno()).order(entity.getOrder()).duedate(entity.getDuedate())
                .content(entity.getContent()).inspector(entity.getInspector()).status(entity.getStatus())
                .build();
    }

    default OrderDTO orderObjectToDTO(Object entityObject) {
        OrderDTO dto = new OrderDTO();
        if (entityObject instanceof Object[] objectArray) {
            if (objectArray[0] instanceof Order order) {
                dto.setOrderno(order.getOrderno());
                dto.setPrcr(order.getPrcr());
                dto.setQuantity(order.getQuantity());
                dto.setStatus(order.getStatus());
                dto.setRegdate(order.getRegdate());
                dto.setModdate(order.getModdate());
                dto.setOrderdate(order.getOrderdate());
            } if (objectArray[1] instanceof Material material) {
                dto.setMaterial(material);
            } if (objectArray[2] instanceof QuotationMtrl quotationmtrl) {
                dto.setQuotationmtrl(quotationmtrl);
            } if (objectArray[3] instanceof Emp emp) {
                dto.setEmp(emp);
            }
            return dto;
        } else {
            return null;
        }
    }

}
