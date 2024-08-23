package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/export")
public class ExportRestController {

    private final ExportService exportService;

    /**
     * 출고 1건 조회
     * @param exportdto
     * @return
     */
    @PostMapping(value = "/read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportDTO> read(@RequestBody ExportDTO exportdto) {
        return ResponseEntity.ok(exportService.read(exportdto.getExportno()));
    }

    /**
     * 출고 처리
     */
    @PostMapping(value = "/ship", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ship(@RequestBody List<ExportDTO> list, @AuthenticationPrincipal UserDTO userDTO) {
        try {
            exportService.Shipment(list, userDTO);
            return ResponseEntity.ok("출고 처리가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
