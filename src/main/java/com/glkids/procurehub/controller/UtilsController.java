package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.PrdcMtrlDetailsDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.PrdcMtrl;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.service.MaterialService;
import com.glkids.procurehub.service.PrdcService;
import com.glkids.procurehub.service.QuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 통계 메뉴 컨트롤러
 */
@RequiredArgsConstructor
@RequestMapping("/utils")
@Controller
public class UtilsController {

    private final QuotationService quotationService;
    private final MaterialService materialService;
    private final PrdcService prdcService;

    /**
     * 통계 화면
     */
    @GetMapping("/graph")
    public String graph(@AuthenticationPrincipal UserDTO userDTO, Model model) {

        model.addAttribute("user", userDTO);
        return "/utils/graph";
    }

    @GetMapping("/calculator")
    public String calculator(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);

        model.addAttribute("title", "계산기");
        return "/utils/calculator";
    }

}
