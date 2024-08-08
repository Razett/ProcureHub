package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.service.MaterialService;
import com.glkids.procurehub.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/rest/order")
public class OrderRestController {

    private final OrderService orderService;

    private final MaterialService materialService;

    public OrderRestController(OrderService orderService, MaterialService materialService) {
        this.orderService = orderService;
        this.materialService = materialService;
    }

    @GetMapping("/add")
    public List<MaterialDTO> search(@RequestParam("mtrlno") Long mtrlno) {
        return Collections.singletonList(materialService.readByFetch(mtrlno));
    }

    // 자재 코드를 통해 자재 정보를 가져오는 API
    @GetMapping("/getMaterialDetailCode")
    public MaterialDTO getMaterialDetailsByCode(@RequestParam("mtrlno") Long mtrlno) {
        System.out.println(mtrlno);
        return materialService.readByFetch(mtrlno);
    }
}

