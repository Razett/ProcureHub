package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.MaterialDTO;
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

    // 자재 코드를 통해 자재 정보를 가져오는 API
    @GetMapping("/details")
    public MaterialDTO getMaterialDetailsByCode(@RequestParam Long mtrlno) {
        System.out.println(mtrlno);
        return materialService.readByFetch(mtrlno);
    }
}
