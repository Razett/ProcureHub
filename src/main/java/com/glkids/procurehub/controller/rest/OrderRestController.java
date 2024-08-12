package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/order")
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping(value = "/list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OrderDTO>> orderExecute(@RequestBody List<Long> orderno) {
        return ResponseEntity.ok(orderService.orderExecute(orderno));
    }

//    @PostMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})

}