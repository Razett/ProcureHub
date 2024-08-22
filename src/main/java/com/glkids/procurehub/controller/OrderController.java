package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.repository.PrcrRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.service.MaterialService;
import com.glkids.procurehub.service.OrderService;
import com.glkids.procurehub.service.PrdcPlanService;
import com.glkids.procurehub.service.QuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 발주 관리 메뉴 컨트롤러
 */
@RequestMapping("/order")
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MaterialService materialService;

    /**
     * 발주 현황
     */
    @GetMapping("/list")
    public String list(@AuthenticationPrincipal UserDTO userDTO, Model model, OrderDTO orderDTO) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "발주 현황");
        model.addAttribute("orderBeforeList", orderService.getOrderListBefore());
        model.addAttribute("orderAfterList", orderService.getOrderListAfter());
        model.addAttribute("orderInspectionReadList", orderService.inspectionRead());
        return "order/list";
    }

    /**
     * 발주 수동 추가
     */
    @GetMapping("/add")
    public String getRegister(@AuthenticationPrincipal UserDTO userDTO, @RequestParam("mtrlno") Long mtrlno, Model model){
        model.addAttribute("user", userDTO);
        model.addAttribute("material", materialService.read(mtrlno));
        return "order/add";
    }

    /**
     * 발주 수동 추가 화면에서 등록 버튼 클릭 시 발주 현황 화면으로
     */
    @PostMapping("/add")
    public String postRegister(@Validated OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사에서 에러가 발생하면 에러 메시지와 함께 다시 폼을 보여줌
            return "order/add"; // 폼이 있는 뷰로 다시 이동
        }

        // MaterialDTO는 사용되지 않으므로 파라미터에서 제거하거나, 필요 시 orderDTO에 포함시키세요.
        orderService.register(orderDTO);
        return "redirect:/order/list";
    }

    @GetMapping("/update")
    public String update(){

        return "order/update";
    }

//    /**
//     * @deprecated
//     * 발주 상세 정보
//     */
//    @Deprecated
//    @GetMapping("/read")
//    public void getRead(Long orderno, Model model){
//        model.addAttribute("orderRead", orderService.read(orderno));
//    }

    /**
     * 발주 전체 내역
     */
    @GetMapping("/totallist")
    public String totalList(@AuthenticationPrincipal UserDTO userDTO, Model model){
        model.addAttribute("user", userDTO);
        model.addAttribute("orderTotalList", orderService.totalList());
        System.out.println("회사"+ orderService.totalList());
        return "/order/totallist";
    }

    /**
     * 검수 현황
     */
    @GetMapping("/inspstatus")
    public void inspectionStatus(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);

    }
}
