package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.dto.OrderInspectionDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.entity.OrderInspection;
import com.glkids.procurehub.service.OrderService;
import com.glkids.procurehub.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/order")
public class OrderRestController {

    private final OrderService orderService;

    /**
     *
     * @param userDTO
     * @param orderDTOList
     * @return
     */
    @PostMapping(value = "/list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OrderDTO>> orderExecute(@AuthenticationPrincipal UserDTO userDTO, @RequestBody List<OrderDTO> orderDTOList) {
        return ResponseEntity.ok(orderService.orderExecute(orderDTOList, userDTO.getEmp()));
    }

    @PostMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void orderUpdate(@RequestBody List<OrderDTO> orderDTOList) {
        orderService.update(orderDTOList);
    }

    @PostMapping(value = "/read", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderDTO> orderRead(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.read(orderDTO.getOrderno()));
    }

    @PostMapping(value = "/insread", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OrderInspectionDTO>> orderInsRead(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.inspectionRead(orderDTO.getOrderno()));
    }

    @Transactional
    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void orderDelete(@RequestBody List<Long> ordernosList){
        for (Long orderno : ordernosList) {
            orderService.changeStatus(orderno, OrderStatus.RETURNED);
        }
    }

    @PostMapping(value="/inspection", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> inspectionRegister(@AuthenticationPrincipal UserDTO userDTO, @RequestBody OrderInspectionDTO orderInspectionDTO){

        return ResponseEntity.ok(orderService.inspectionRegister(orderInspectionDTO, userDTO.getEmp()));
    }


}