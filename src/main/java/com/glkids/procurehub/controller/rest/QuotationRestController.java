package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.*;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class QuotationRestController {

    @Autowired
    private QuotationService quotationService;
    @Autowired
    private QuotationMtrlRepository quotationMtrlRepository;

    /**
     * 견적서 저장
     * @param quotationDTO
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/quotation/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> saveQuotation(@RequestBody QuotationDTO quotationDTO , @AuthenticationPrincipal UserDTO userDTO) {
        quotationDTO.setEmp(userDTO.getEmp());
        Quotation savedQuotation = quotationService.saveQuotation(quotationDTO);
        return ResponseEntity.ok(savedQuotation.getQtno());
    }

    /**
     * 견적서파일
     * @param quotationFileDTO
     * @return
     */
    @PostMapping(value = "/quotationFile/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveQuotationFile(@RequestBody QuotationFileDTO quotationFileDTO) {
        QuotationFile savedFile = quotationService.saveQuotationFile(quotationFileDTO);
        return ResponseEntity.ok("파일저장 아이디는 : " + savedFile.getQtfno());
    }

    /**
     * 견적서의 자재정보 저장
     * @param quotationMtrlDTO
     * @param userDTO
     * @return
     */
    @PostMapping("/quotationMtrl/save")
    public Boolean saveQuotationMtrl(@RequestBody List<QuotationMtrlDTO> quotationMtrlDTO , @AuthenticationPrincipal UserDTO userDTO) {
        return quotationService.saveQuotationMtrl(quotationMtrlDTO, userDTO);
    }

    @GetMapping("/api/companies")
    public List<ContractorDTO> getCompany(@RequestParam("mtrlno") Long mtrlno){
        List<QuotationMtrl> quotationMtrls = quotationMtrlRepository.findByMaterial(mtrlno);

        return quotationMtrls.stream()
                .map(q -> new ContractorDTO(q.getQuotation().getContractor().getName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/api/orderInfo")
    public QuotationMtrlDTO getOrderInfo(@RequestParam("mtrlno") Long mtrlno, @RequestParam("contractorName") String contractorName) {
        List<QuotationMtrl> quotationMtrls = quotationMtrlRepository.findByMaterial(mtrlno);

        for (QuotationMtrl qm : quotationMtrls) {
            if (qm.getQuotation().getContractor().getName().equals(contractorName)) {
                return quotationService.quotationMtrlEntityToDTO(qm); // 필요한 정보를 DTO로 변환해서 반환
            }
        }
        return null;
    }

}
