package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.EmpDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.repository.OrderRepository;
import com.glkids.procurehub.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final ProcurementService procurementService;
    private final OrderServiceImpl orderServiceimpl;
    private final EmpService empService;
    private final ImportServiceImpl importServiceImpl;
    private final ExportServiceImpl exportServiceImpl;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDTO userDTO, Model model, EmpDTO empDTO) {
        model.addAttribute("user", userDTO);
        model.addAttribute("empCount" , empService.getEmpCounts());
        model.addAttribute("empUserCount", empService.getCurrentUserEmpCounts());

        model.addAttribute("redCount", procurementService.countRedStatus()); // 긴급 상태 개수
        model.addAttribute("yellowCount", procurementService.countYellowStatus()); // 경고 상태 개수
        model.addAttribute("BlueCount" , procurementService.countBlueStatus());
        model.addAttribute("totalPlans", procurementService.countTotalProcurementPlans());

        model.addAttribute("orderRedCount" , orderServiceimpl.countRedStatus());
        model.addAttribute("orderYellowCount" , orderServiceimpl.countYellowStatus());
        model.addAttribute("orderBlueCount" , orderServiceimpl.countBlueStatus());
        model.addAttribute("totalOrder", orderServiceimpl.countTotalOrder());

        model.addAttribute("importRedCount" , importServiceImpl.countRedStatus());
        model.addAttribute("importBlueCount" , importServiceImpl.countBlueStatus());
        model.addAttribute("totalImport" , importServiceImpl.countTotalImport());
        model.addAttribute("importYellowCount" , importServiceImpl.countYellowStatus());

        model.addAttribute("exportRedCount" , exportServiceImpl.countRedStatus());
        model.addAttribute("totalExport" , exportServiceImpl.countTotalStatus());

        return "/index";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }
}
