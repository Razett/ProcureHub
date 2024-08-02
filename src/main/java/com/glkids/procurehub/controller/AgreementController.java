package com.glkids.procurehub.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 계약 관리 메뉴 컨트롤러
 */
@RequestMapping("/Agreement")
@Controller
@RequiredArgsConstructor
public class AgreementController {
    @GetMapping("/register")
    public void register() {

    }
}
