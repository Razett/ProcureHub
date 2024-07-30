package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.MaterialGroupDTO;
import com.glkids.procurehub.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/material")
public class MaterialRestController {

    private final MaterialService materialService;

    @GetMapping("/childgroup")
    public List<MaterialGroupDTO> getChildGroupList(String grpcode) {
        return materialService.getChildMaterialGroups(grpcode);
    }
}
