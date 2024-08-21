package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.ImportInspectionDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/import")
public class ImportRestController {

    private final ImportService importService;

    @PostMapping(value = "read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportDTO> read(@RequestBody ImportDTO importDTO) {
        return ResponseEntity.ok(importService.readRestful(importDTO.getImportno()));
    }

    @PostMapping(value = "insread", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportInspectionDTO> readInspection(@RequestBody ImportDTO importDTO) {
        return ResponseEntity.ok(importService.readInspection(importDTO.getImportno()));
    }

    @PostMapping(value = "execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> execute(@AuthenticationPrincipal UserDTO userDTO, @RequestBody List<ImportDTO> list) {
        return ResponseEntity.ok(importService.executeImport(list, userDTO.getEmp()));
    }

    @PostMapping(value = "insupdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateInspection(@AuthenticationPrincipal UserDTO userDTO, @RequestBody ImportInspectionDTO importInspectionDTO) {
        return ResponseEntity.ok(importService.updateInspection(importInspectionDTO, userDTO.getEmp()));
    }
}
