package com.glkids.procurehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 입고 관리 메뉴 컨트롤러
 */
@RequestMapping("/import")
@Controller
public class ImportController {

    /**
     * 입고 현황
     */
    @GetMapping("/list")
    public String list() {
        return "import/list";
    }

    /**
     * 입고 현황 수정
     */
    @GetMapping("/update")
    public void getUpdate() {}

    /**
     * 입고 현황 수정 화면에서 저장 버튼 클릭 시 완료 후 입고 현황 화면으로
     */
    @PostMapping("/update")
    public String postUpdate() {
        return "import/list";
    }

    /**
     * 입고 상세 정보
     */
    @GetMapping("/read")
    public void read() {}

    /**
     * 입고 전체 내역
     */
    @GetMapping("/totallist")
    public String totalList(){
        return "import/totallist";
    }

}
