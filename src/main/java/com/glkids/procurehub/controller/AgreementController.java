package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.*;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 계약 관리 메뉴 컨트롤러
 */
@RequestMapping("/agreement")
@Controller
@RequiredArgsConstructor
public class AgreementController {

    private final QuotationService quotationService;

    private  final AgreementService agreementService;

    private  final MaterialService materialService;


    @GetMapping("/register")
    public String register(Model model, @RequestParam("qtno") Long qtno ) {

        model.addAttribute("qtno", quotationService.read(qtno));
        model.addAttribute("qtnomtrl", quotationService.quotationMtrlList(qtno));
        model.addAttribute("material", materialService.list());
        return "agreement/register";
    }

    @PostMapping("proregister")
    public String proregister(AgreementDTO agreementDTO){
        agreementService.register(agreementDTO);
        return "redirect:/contractor/list";
    }
}
