package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;  // DTO 클래스 임포트
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.*;
import com.glkids.procurehub.service.QuotationMtrlService;
import com.glkids.procurehub.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotationRestController {

    @Autowired
    private QuotationService quotationService;

    @PostMapping(value = "/quotation/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> saveQuotation(@RequestBody QuotationDTO quotationDTO) {
        Quotation savedQuotation = quotationService.saveQuotation(quotationDTO);
        return ResponseEntity.ok(savedQuotation.getQtno());
    }

    @PostMapping(value = "/quotationFile/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveQuotationFile(@RequestBody QuotationFileDTO quotationFileDTO) {
        QuotationFile savedFile = quotationService.saveQuotationFile(quotationFileDTO);
        return ResponseEntity.ok("파일저장 아이디는 : " + savedFile.getQtfno());
    }

    @PostMapping("/quotationMtrl/save")
    public ResponseEntity<String> saveQuotationMtrl(@RequestBody QuotationMtrlDTO quotationMtrlDTO) {
        quotationService.saveQuotationMtrl(quotationMtrlDTO);
        return ResponseEntity.ok("견적서 자재 저장 성공.");
    }
}
