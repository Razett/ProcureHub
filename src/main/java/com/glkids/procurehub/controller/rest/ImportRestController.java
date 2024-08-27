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

    /**
     * 입고건 1건 조회
     * @param importDTO
     * @return
     */
    @PostMapping(value = "/read", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportDTO> read(@RequestBody ImportDTO importDTO) {
        return ResponseEntity.ok(importService.readRestful(importDTO.getImportno()));
    }

    /**
     * 해당 입고건의 입고 검수건 조회
     * @param importDTO
     * @return
     */
    @PostMapping(value = "/insread", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportInspectionDTO> readInspection(@RequestBody ImportDTO importDTO) {
        return ResponseEntity.ok(importService.readInspection(importDTO.getImportno()));
    }

    /**
     * 입고처리
     * @param userDTO
     * @param list
     * @return
     */
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> execute(@AuthenticationPrincipal UserDTO userDTO, @RequestBody List<ImportDTO> list) {
        return ResponseEntity.ok(importService.executeImport(list, userDTO.getEmp()));
    }

    /**
     * 입고 검수처리
     * @param userDTO
     * @param importInspectionDTO
     * @return
     */
    @PostMapping(value = "/insupdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateInspection(@AuthenticationPrincipal UserDTO userDTO, @RequestBody ImportInspectionDTO importInspectionDTO) {
        return ResponseEntity.ok(importService.updateInspection(importInspectionDTO, userDTO.getEmp()));
    }

    /**
     * 입고 수량 업데이트
     *
     * @param importDTOList 업데이트할 입고 데이터 목록
     * @return 성공 또는 실패 상태 반환
     */
    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateImports(@RequestBody List<ImportDTO> importDTOList) {
        try {
            importService.update(importDTOList);
            return ResponseEntity.ok().build();  // 성공 시 200 OK 응답 반환
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();  // 실패 시 400 Bad Request 응답 반환
        }
    }
}
