package com.glkids.procurehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 계약 관리 메뉴 컨트롤러
 */
@RequestMapping("/contract")
@Controller
public class ContractController {

    /**
     * 업체 목록
     */
    @GetMapping("/list")
    public String List() {
        return "/contract/list";
    }

    /**
     * 업체 등록
     */
    @GetMapping("/register")
    public void getRegister() {}

    /**
     * 업체 등록 버튼 클릭 시 목록 화면으로
     */
    @PostMapping("/register")
    public String postRegister() {
        return "/contract/list";
    }

    /**
     * 견적 목록
     */
    @GetMapping("/quolist")
    public String quoList() {
        return "/contract/quolist";
    }

    /**
     * 견적 등록
     */
    @GetMapping("/quoregister")
    public void getQuoRegister() {}

    /**
     * 견적 등록 버튼 클릭 시 목록 화면으로
     */
    @PostMapping("/quoregister")
    public String postQuoRegister() {
        return "/contract/quolist";
    }


}
