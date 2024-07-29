package com.glkids.procurehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 통계 메뉴 컨트롤러
 */
@RequestMapping("/statistics")
@Controller
public class StatisticsController {

    /**
     * 통계 화면
     */
    @GetMapping("/graph")
    public String graph() {
        return "/statistics/graph";
    }
}
