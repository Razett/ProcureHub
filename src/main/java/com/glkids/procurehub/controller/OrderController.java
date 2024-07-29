package com.glkids.procurehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 발주 관리 메뉴 컨트롤러
 */
@RequestMapping("/order")
@Controller
public class OrderController {

    /**
     * 발주 현황
     */
    @GetMapping("/list")
    public String list() {
        return "order/list";
    }

    /**
     * 발주 수동 추가
     */
    @GetMapping("/add")
    public void getRegister(){}

    /**
     * 발주 수동 추가 화면에서 등록 버튼 클릭 시 발주 현황 화면으로
     */
    @PostMapping("/add")
    public String postRegister(){
        return "order/list";
    }

    /**
     * @deprecated
     * 발주 진행 수정
     */
    @Deprecated
    @GetMapping("/update")
    public void getUpdate(){}

    /**
     * @deprecated
     * 발주 진행 사항 수정 후 저장 버튼 클릭 시 발주 현황 화면으로
     */
    @Deprecated
    @PostMapping("/update")
    public String postUpdate(){
        return "order/list";
    }

    /**
     * @deprecated
     * 발주 상세 정보
     */
    @Deprecated
    @GetMapping("/read")
    public void getRead(){}

    /**
     * 발주 전체 내역
     */
    @GetMapping("/totallist")
    public String totalList(){
        return "order/totallist";
    }

}
