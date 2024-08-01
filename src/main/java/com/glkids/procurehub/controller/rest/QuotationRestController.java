package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;  // DTO 클래스 임포트
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.*;
import com.glkids.procurehub.service.QuotationMtrlService;
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
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationFileRepository quotationFileRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private QuotationMtrlRepository quotationMtrlRepository;

    @Autowired
    private EmpRepository empRepository;



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

        // QuotationFile 엔터티 생성 및 저장
        QuotationFile quotationFile = QuotationFile.builder()
                .quotation(Quotation.builder().qtno(quotationFileDTO.getQtno()).build())
                .uuid(quotationFileDTO.getUuid())
                .name(quotationFileDTO.getName())
                .url(quotationFileDTO.getUrl())
                .build();

        QuotationFile savedFile = quotationFileRepository.save(quotationFile);
        return ResponseEntity.ok("파: " + savedFile.getQtfno());
    }

    // 자재 정보 저장
    @PostMapping("/quotationMtrl/save")
    public ResponseEntity<String> saveQuotationMtrl(@RequestBody QuotationMtrlDTO quotationMtrlDTO) {
        // Emp 테이블에서 사원 정보 가져오기
        Emp emp = empRepository.findById(quotationMtrlDTO.getEmpId())
                .orElseThrow(() -> new RuntimeException("사원을 찾을 수 없습니다."));

        // Material 테이블에서 자재 정보 가져오기
        Material material = materialRepository.findById(quotationMtrlDTO.getMaterialId())
                .orElseThrow(() -> new RuntimeException("자재를 찾을 수 없습니다."));

        // Quotation 테이블에서 견적 정보 가져오기
        Quotation quotation = quotationRepository.findById(quotationMtrlDTO.getQuotationId())
                .orElseThrow(() -> new RuntimeException("견적을 찾을 수 없습니다."));

        QuotationMtrl quotationMtrl = QuotationMtrl.builder()
                .leadtime(quotationMtrlDTO.getLeadtime())
                .quantity(quotationMtrlDTO.getQuantity())
                .totalprice(quotationMtrlDTO.getTotalprice())
                .unitprice(quotationMtrlDTO.getUnitprice())
                .emp(emp)
                .material(material)
                .quotation(quotation)
                .build();

        quotationMtrlRepository.save(quotationMtrl);

        return ResponseEntity.ok("견적 자재 정보가 성공적으로 저장되었습니다.");
    }
}
