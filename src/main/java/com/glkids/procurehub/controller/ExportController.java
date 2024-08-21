package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.ExportDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.repository.ExportRepository;
import com.glkids.procurehub.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 출고 관리 메뉴 컨트롤러
 */
@RequestMapping("/export")
@Controller
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;
    /**
     * 출고 현황
     */
    @GetMapping("/list")
    public String list(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "출고 현황");
        model.addAttribute("export" , exportService.getExportListBefore());
        return "export/list";
    }

    /**
     * 출고 전체 내역
     */
    @GetMapping("/totallist")
    public String totalList(@AuthenticationPrincipal UserDTO userDTO, Model model, String type, String input) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "출고 전체");
        model.addAttribute("exportlist", exportService.totalList(type, input));
        return "/export/totallist";
    }



}
