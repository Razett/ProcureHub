package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.service.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST 컨트롤러 클래스
 */
@RestController
@RequiredArgsConstructor
public class ContractorRestController {

    private final ContractorService contractorService;

    /**
     * 회사명을 통해 회사 정보를 가져오는 메서드
     * @param contractorName 회사명
     * @return 회사 정보 DTO
     */
    @GetMapping("/contractor/getContractorDetailsByName")
    public ResponseEntity<ContractorDTO> getContractorDetailsByName(@RequestParam String contractorName) {
        try {
            ContractorDTO contractorDTO = contractorService.findByName(contractorName);
            return ResponseEntity.ok(contractorDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // or appropriate error handling
        }
    }
}
