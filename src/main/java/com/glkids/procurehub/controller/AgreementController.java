package com.glkids.procurehub.controller;

import com.glkids.procurehub.entity.Material;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 계약 관리 메뉴 컨트롤러
 */
@RequestMapping("/agreement")
@Controller
@RequiredArgsConstructor
public class AgreementController {
    @GetMapping("/register")
    public void register(Model model, Material material) {
        model.addAttribute("material", material);
    }
}
