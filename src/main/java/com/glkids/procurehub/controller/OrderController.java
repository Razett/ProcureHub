package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 발주 관리 메뉴 컨트롤러
 */
@RequestMapping("/order")
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 발주 현황
     */
    @GetMapping("/list")
    public String list(Model model, OrderDTO orderDTO) {
        model.addAttribute("orderList", orderService.list());
        return "order/list";
    }

    /**
     * 발주 수동 추가
     */
    @GetMapping("/add")
    public void getRegister(OrderDTO orderDTO, Model model){}

    /**
     * 발주 수동 추가 화면에서 등록 버튼 클릭 시 발주 현황 화면으로
     */
    @PostMapping("/add")
    public String postRegister(OrderDTO orderDTO){
        orderService.register(orderDTO);
        return "redirect:/order/list";
    }

    /**
     * @deprecated
     * 발주 진행 수정
     */
    @Deprecated
    @GetMapping("/update")
    public void getUpdate(Long orderno, Model model){}

    /**
     * @deprecated
     * 발주 진행 사항 수정 후 저장 버튼 클릭 시 발주 현황 화면으로
     */
    @Deprecated
    @PostMapping("/update")
    public String postUpdate(Model model, Order order){
        model.addAttribute("order", order);
        return "order/list";
    }

    /**
     * @deprecated
     * 발주 상세 정보
     */
    @Deprecated
    @GetMapping("/read")
    public void getRead(Long orderno, Model model){
        model.addAttribute("orderRead", orderService.read(orderno));
    }

    /**
     * 발주 전체 내역
     */
    @GetMapping("/totallist")
    public String totalList(Model model, OrderDTO orderDTO){
        model.addAttribute("orderTotalList", orderService.totalList());
        return "/order/totallist";
    }

    /**
     * 검수 현황
     */
    @GetMapping("/inspstatus")
    public String inspectionStatus() {
        return "/order/inspstatus";
    }
}
