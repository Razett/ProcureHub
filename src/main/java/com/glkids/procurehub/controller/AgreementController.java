package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.*;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String register(@AuthenticationPrincipal UserDTO userDTO, Model model, @RequestParam("qtno") Long qtno ) {
        model.addAttribute("user", userDTO);

        model.addAttribute("qtno", quotationService.read(qtno));
        model.addAttribute("qtnomtrl", quotationService.readQuotationMtrlList(qtno));

        return "agreement/register";
    }

    @PostMapping("/register")
    public String proregister(@AuthenticationPrincipal UserDTO userDTO, AgreementDTO agreementDTO, @RequestParam("startdateText") String startdateText, @RequestParam("enddateText") String enddateText, RedirectAttributes redirectAttributes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        agreementDTO.setContractor(Contractor.builder().corno(agreementDTO.getCorno()).build());
        agreementDTO.setQuotation(Quotation.builder().qtno(agreementDTO.getQtno()).build());
        agreementDTO.setEmp(userDTO.getEmp());

        agreementDTO.setStartdate(LocalDate.parse(startdateText, formatter).atStartOfDay());
        agreementDTO.setEnddate(LocalDate.parse(enddateText, formatter).atStartOfDay());

        Boolean result = agreementService.register(agreementDTO);
        if (result) {
            redirectAttributes.addFlashAttribute("msg", "계약이 등록되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "계약등록에 실패하였습니다.");
        }
        return "redirect:/contractor/quoread?qtno=" + agreementDTO.getQtno();
    }

}
