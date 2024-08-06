package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/order")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @PostMapping(value = "/list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public List<OrderDTO> orderExecute(@RequestBody List<String> orderKey) {
//        return orderService.orderExecute(orderKey);
//    }
}

