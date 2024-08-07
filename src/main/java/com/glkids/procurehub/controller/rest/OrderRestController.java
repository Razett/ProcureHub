package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/order")
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping(value = "/list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OrderDTO>> orderExecute(@RequestBody List<Long> orderno) {
        System.out.println(orderno);
        if (orderno == null || orderno.isEmpty() || orderno.contains(null)) {
            throw new IllegalArgumentException("Order keys must not be null or empty and must contain valid IDs");
        }
        return ResponseEntity.ok(orderService.orderExecute(orderno));
    }

}
