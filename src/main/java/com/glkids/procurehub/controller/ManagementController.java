package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementController {

    private final EmpService empService;

    @GetMapping("/work")
    public String status(@AuthenticationPrincipal UserDTO userDTO, Model model, RedirectAttributes redirectAttributes){

        model.addAttribute("empCounts", empService.getEmpCounts()); // 모든 사원 일일 건 수
        model.addAttribute("empMonthCounts", empService.getMonthCounts()); // 모든 사원 한달 건 수
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "관리");
        if(userDTO.getEmp().getDept().getDeptno()==1L){
            return "/management/work";

        }else {
            redirectAttributes.addFlashAttribute("msg", "권한이 없습니다.");
        }
        return  "redirect:/";
    }
}
