package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;  // DTO 클래스 임포트
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuotationRestController {

    @Autowired
    private QuotationService quotationService;

    @PostMapping(value = "/quotation/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> saveQuotation(@RequestBody QuotationDTO quotationDTO , @AuthenticationPrincipal UserDTO userDTO) {
        quotationDTO.setEmp(userDTO.getEmp());
        Quotation savedQuotation = quotationService.saveQuotation(quotationDTO);
        return ResponseEntity.ok(savedQuotation.getQtno());
    }

    @PostMapping(value = "/quotationFile/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveQuotationFile(@RequestBody QuotationFileDTO quotationFileDTO) {
        QuotationFile savedFile = quotationService.saveQuotationFile(quotationFileDTO);
        return ResponseEntity.ok("파일저장 아이디는 : " + savedFile.getQtfno());
    }

    @PostMapping("/quotationMtrl/save")
    public Boolean saveQuotationMtrl(@RequestBody List<QuotationMtrlDTO> quotationMtrlDTO , @AuthenticationPrincipal UserDTO userDTO) {
        return quotationService.saveQuotationMtrl(quotationMtrlDTO, userDTO);
    }
}
