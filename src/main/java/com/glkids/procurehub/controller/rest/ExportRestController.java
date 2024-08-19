package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/export")
public class ExportRestController {

    private final ExportService exportService;

    @PostMapping(value = "/read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportDTO> read(@RequestBody ExportDTO exportdto) {
        return ResponseEntity.ok(exportService.read(exportdto.getExportno()));
    }
}
