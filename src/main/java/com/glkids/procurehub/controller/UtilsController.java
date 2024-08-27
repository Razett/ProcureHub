package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 통계 메뉴 컨트롤러
 */
@RequestMapping("/utils")
@Controller
public class UtilsController {

    /**
     * 통계 화면
     */
    @GetMapping("/graph")
    public String graph(@AuthenticationPrincipal UserDTO userDTO, Model model) {

        model.addAttribute("user", userDTO);
        return "/utils/graph";
    }
}
