package com.glkids.procurehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 출고 관리 메뉴 컨트롤러
 */
@RequestMapping("/export")
@Controller
public class ExportController {

    /**
     * 출고 현황
     */
    @GetMapping("/list")
    public String list() {
        return "/export/list";
    }

    /**
     * 출고 전체 내역
     */
    @GetMapping("/totallist")
    public String totalList() {
        return "/export/totallist";
    }
}
