package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;  // DTO 클래스 임포트
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.*;
import com.glkids.procurehub.service.QuotationMtrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotationRestController {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationFileRepository quotationFileRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private QuotationMtrlRepository quotationMtrlRepository;

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private QuotationMtrlService quotationMtrlService;

    @PostMapping(value = "/quotation/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> saveQuotation(@RequestBody QuotationDTO quotationDTO) {

        Contractor contractor = Contractor.builder()
                .corno(quotationDTO.getContractor().getCorno())
                .build();
        Emp emp = Emp.builder()
                .empno(quotationDTO.getEmp().getEmpno())
                .build();
        Quotation quotation = Quotation.builder()
                .contractor(contractor)
                .emp(emp)
                .title(quotationDTO.getTitle())
                .content(quotationDTO.getContent())
                .status(quotationDTO.getStatus())
                .build();

        Quotation savedQuotation = quotationRepository.save(quotation);
        return ResponseEntity.ok(savedQuotation.getQtno());
    }

    @PostMapping(value = "/quotationFile/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveQuotationFile(@RequestBody QuotationFileDTO quotationFileDTO) {
        // Quotation 엔터티 가져오기
        Quotation quotation = quotationRepository.findById(quotationFileDTO.getQtno())
                .orElseThrow(() -> new IllegalArgumentException("Invalid quotation ID: " + quotationFileDTO.getQtno()));

        // QuotationFile 엔터티 생성 및 저장
        QuotationFile quotationFile = QuotationFile.builder()
                .quotation(quotation)
                .uuid(quotationFileDTO.getUuid())
                .name(quotationFileDTO.getName())
                .url(quotationFileDTO.getUrl())
                .build();

        QuotationFile savedFile = quotationFileRepository.save(quotationFile);
        return ResponseEntity.ok("File metadata saved successfully with ID: " + savedFile.getQtfno());
    }

    @PostMapping(value = "/quotationMtrl/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveQuotationMtrl(@RequestBody QuotationMtrlDTO quotationMtrlDTO) {
        // 디버깅용 로그 추가
        System.out.println("Quotation ID: " + quotationMtrlDTO.getQuotationId());
        System.out.println("Material ID: " + quotationMtrlDTO.getMaterialId());
        System.out.println("Employee ID: " + quotationMtrlDTO.getEmpId());

        QuotationMtrl quotationMtrl = convertToEntity(quotationMtrlDTO);
        quotationMtrlService.saveQuotationMtrl(quotationMtrl);
        return ResponseEntity.ok("QuotationMtrl saved successfully");
    }

    private QuotationMtrl convertToEntity(QuotationMtrlDTO quotationMtrlDTO) {
        Quotation quotation = quotationRepository.findById(quotationMtrlDTO.getQuotationId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid quotation ID: " + quotationMtrlDTO.getQuotationId()));

        Material material = materialRepository.findById(quotationMtrlDTO.getMaterialId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid material ID: " + quotationMtrlDTO.getMaterialId()));

        Emp emp = empRepository.findById(quotationMtrlDTO.getEmpId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + quotationMtrlDTO.getEmpId()));

        return QuotationMtrl.builder()
                .quotation(quotation)
                .material(material)
                .emp(emp)
                .quantity(quotationMtrlDTO.getQuantity())
                .unitprice(quotationMtrlDTO.getUnitprice())
                .totalprice(quotationMtrlDTO.getTotalprice())
                .leadtime(quotationMtrlDTO.getLeadtime())
                .build();
    }
}
