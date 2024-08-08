package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.*;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.MaterialFile;
import com.glkids.procurehub.service.MaterialService;
import com.glkids.procurehub.service.MaterialServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/material")
public class MaterialRestController {

    private final MaterialService materialService;
    private final MaterialServiceImpl materialServiceImpl;

    @GetMapping("/childgroup")
    public List<MaterialGroupDTO> getChildGroupList(String grpcode) {
        return materialService.getChildMaterialGroups(grpcode);
    }

    // 자재 코드를 통해 자재 정보를 가져오는 API
    @GetMapping("/getMaterialDetailsByCode")
    public MaterialDTO getMaterialDetailsByCode(@RequestParam("mtrlno") Long mtrlno) {
        System.out.println(mtrlno);
        return materialService.readByFetch(mtrlno);
    }

    @GetMapping("/search")
    public List<MaterialDTO> search(@RequestParam("mtrlno") Long mtrlno) {
        return Collections.singletonList(materialService.readByFetch(mtrlno));
    }

    @GetMapping("/searchByName")
    public List<Material> searchByName(@RequestParam("name") String name) {
        return materialService.findByNameContaining(name);
    }
    /**
     * 자재 창고 코드의 중복 검사를 수행
     *
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
    public ResponseEntity<MaterialWarehouseDTO> registerWarehouse(@RequestBody MaterialWarehouseDTO materialWarehouseDTO) {
        materialWarehouseDTO.setWrhscode(materialWarehouseDTO.getWrhscode().trim().toUpperCase());
        materialWarehouseDTO.setWrhsname(materialWarehouseDTO.getWrhsname().trim().toUpperCase());
        return ResponseEntity.ok(materialService.registerMaterialWarehouse(materialWarehouseDTO));
    }

    @PostMapping(value = "/warehouseupdate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MaterialWarehouseDTO> updateWarehouse(@RequestBody MaterialWarehouseDTO materialWarehouseDTO) {
        materialWarehouseDTO.setWrhscode(materialWarehouseDTO.getWrhscode().trim().toUpperCase());
        materialWarehouseDTO.setWrhsname(materialWarehouseDTO.getWrhsname().trim().toUpperCase());
        if (materialWarehouseDTO.getWrhscode().equals("ETC")) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(materialService.registerMaterialWarehouse(materialWarehouseDTO));
    }

    @PostMapping(value = "/warehousedelete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteWarehouse(@RequestBody MaterialWarehouseDTO materialWarehouseDTO) {
        materialWarehouseDTO.setWrhscode(materialWarehouseDTO.getWrhscode().trim().toUpperCase());
        if (materialWarehouseDTO.getWrhscode().equals("ETC")) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(materialService.deleteMaterialWarehouse(materialWarehouseDTO.getWrhscode()));
    }

    @PostMapping(value = "/materialFile/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MaterialFile> saveQuotationFile(@RequestBody MaterialFileDTO materialFileDTO) {
        materialFileDTO.setMaterial(Material.builder().mtrlno(materialFileDTO.getMtrlno()).build());
        MaterialFile materialFile = materialService.saveMaterialFile(materialFileDTO);
        return ResponseEntity.ok(materialFile);
    }
}

