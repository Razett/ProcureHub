package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.MaterialGroupDTO;
import com.glkids.procurehub.dto.MaterialWarehouseDTO;
import com.glkids.procurehub.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
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
    public MaterialDTO getMaterialDetailsByCode(@RequestParam("mtrlno") Long mtrlno) {
        System.out.println(mtrlno);
        return materialService.readByFetch(mtrlno);
    }

    /**
     * 자재 창고 코드의 중복 검사를 수행
     * @param wrhscode 중복 검사를 수행할 창고코드
     * @return 중복 시 false, 사용가능 시 true.
     */
    @GetMapping(value = "/verify/wrhscode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> verifyWrhsCode(@RequestParam String wrhscode) {
        System.out.println(wrhscode.trim().toUpperCase());
        System.out.println(materialService.verifyWrhscode(wrhscode.trim().toUpperCase()));
        return materialService.verifyWrhscode(wrhscode.trim().toUpperCase()) ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
    }

    @PostMapping(value = "/warehouseregister", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MaterialWarehouseDTO> registerWarehouser(@RequestBody MaterialWarehouseDTO materialWarehouseDTO) {
        materialWarehouseDTO.setWrhscode(materialWarehouseDTO.getWrhscode().trim().toUpperCase());
        materialWarehouseDTO.setWrhsname(materialWarehouseDTO.getWrhsname().trim().toUpperCase());
        return ResponseEntity.ok(materialService.registerMaterialWarehouse(materialWarehouseDTO));
    }
}
