package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.service.ProcurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final ProcurementService procurementService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("redCount", procurementService.countRedStatus()); // 긴급 상태 개수
        model.addAttribute("yellowCount", procurementService.countYellowStatus()); // 경고 상태 개수
        model.addAttribute("totalPlans", procurementService.countTotalProcurementPlans());

        return "index";
    }

    @GetMapping("/login")
    public void login(){

    }
}
