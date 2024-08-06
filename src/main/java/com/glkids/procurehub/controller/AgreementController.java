package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.*;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 계약 관리 메뉴 컨트롤러
 */
@RequestMapping("/agreement")
@Controller
@RequiredArgsConstructor
public class AgreementController {

    private final QuotationService quotationService;

    private  final AgreementService agreementService;
    private final ContractorService contractorService;


    @GetMapping("/register")
    public String register(Model model, @RequestParam("qtno") Long qtno ) {

        model.addAttribute("qtno", quotationService.read(qtno));
        model.addAttribute("qtnomtrl", quotationService.quotationMtrlList(qtno));

        return "agreement/register";
    }

    @PostMapping("/register")
    public String proregister(AgreementDTO agreementDTO, @RequestParam("startdateText") String startdateText, @RequestParam("enddateText") String enddateText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        agreementDTO.setContractor(Contractor.builder().corno(agreementDTO.getCorno()).build());
        agreementDTO.setQuotation(Quotation.builder().qtno(agreementDTO.getQtno()).build());
        agreementDTO.setEmp(Emp.builder().empno(201758030L).build());

        agreementDTO.setStartdate(LocalDate.parse(startdateText, formatter).atStartOfDay());
        agreementDTO.setEnddate(LocalDate.parse(enddateText, formatter).atStartOfDay());

        agreementService.register(agreementDTO);
        return "redirect:/contractor/list";
    }

}
