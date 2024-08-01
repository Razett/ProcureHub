package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Quotation;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.service.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 계약 관리 메뉴 컨트롤러
 */
@RequestMapping("/contractor")
@Controller
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;

    /**
     * 업체 목록
     */
    @GetMapping("/list")
    public String List(Model model, ContractorDTO contractorDTO) {
        model.addAttribute("contractorList", contractorService.list());
        return "/contractor/list";
    }

    /**
     * 업체 상세정보
     * @param corno 사업자 등록 번호
     * @return /contractor/get 을 요청하여 업체 상세페이지를 표시합니다.
     */
    @GetMapping("/read")
    public String read(Long corno, Model model) {
        model.addAttribute("contractorRead", contractorService.read(corno));
        return "/contractor/read";
    }

    /**
     * 업체 등록 화면
     */
    @GetMapping("/register")
    public void getRegister(ContractorDTO contractorDTO, Model model) {}

    /**
     * 업체 등록 처리
     */
    @PostMapping("/register")
    public String postRegister(ContractorDTO contractorDTO) {
        contractorService.register(contractorDTO);
        return "redirect:/contractor/list";
    }

    /**
     * 업체 수정 화면
     */
    @GetMapping("/update")
    public String getUpdate(Long corno, Model model) {
        return "/contractor/update";
    }

    /**
     * 업체 수정 처리
     */
    @PostMapping("/update")
    public String postUpdate(Model model, Contractor contractor) {
        // 데이터를 처리하는 로직 추가
        model.addAttribute("contractor", contractor);
        return "/contractor/read?corno=" + contractor.getCorno();
    }

    /**
     * 견적 목록
     */
    @GetMapping("/quolist")
    public String quoList(Model model) {
        model.addAttribute("quotationList", contractorService.quoList());
        return "/contractor/quolist";
    }

    /**
     * 견적 등록
     */
    @GetMapping("/quoregister")
    public void getQuoRegister(Long corno, Model model) {

    }

    /**
     * 견적 등록 처리
     */
    @PostMapping("/quoregister")
    public String postQuoRegister(QuotationDTO quotationDTO, QuotationMtrl quotationMtrl) {
        contractorService.quoRegister(quotationDTO);
        return "/contractor/quolist";
    }

}
