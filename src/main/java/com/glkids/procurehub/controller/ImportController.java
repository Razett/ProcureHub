package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String list(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "입고 현황");

        return "import/list";
    }

    /**
     * 입고 현황 수정
     */
    @Deprecated
    @GetMapping("/update")
    public void getUpdate(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "입고 수정");
    }

    /**
     * 입고 현황 수정 화면에서 저장 버튼 클릭 시 완료 후 입고 현황 화면으로
     */
    @Deprecated
    @PostMapping("/update")
    public String postUpdate() {

        return "import/list";
    }

    /**
     * @deprecated
     * 입고 상세 정보
     */
    @Deprecated
    @GetMapping("/read")
    public void read() {}

    /**
     * 입고 전체 내역
     */
    @GetMapping("/totallist")
    public String totalList(@AuthenticationPrincipal UserDTO userDTO, Model model){
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "입고 전체");

        return "import/totallist";
    }

    /**
     * 입고 검수 현황
     */
    @GetMapping("/checkstatus")
    public String checkStatus(){
        return "import/checkstatus";
    }

}
