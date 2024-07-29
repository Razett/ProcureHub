package com.glkids.procurehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 자재 관리 메뉴 컨트롤러
 */
@RequestMapping("/material")
@Controller
public class MaterialController {

    /**
     * 자재 목록
     */
    @GetMapping("/list")
    public String List() {

        return "/material/list";
    }

    /**
     * 자재 수정
     */
    @GetMapping("/update")
    public String update() {

        return "/material/update";
    }

    /**
     * 자재상세보기 화면
     */
    @GetMapping("/materialdetail")
    public String materialdetail() {
        return "/material/materialdetail";
    }

    /**
     * 자재 수정버튼 누르고 자재상세보기 화면
     */
    @PostMapping("/materialdetail")
    public String Postupdate() {

        return "/material/materialdetail";
    }



    /**
     * 자재 등록
     */
    @GetMapping("/register")
    public void getRegister() {}

    /**
     * 자재 등록 버튼 클릭 시 목록 화면으로
     */
    @PostMapping("/register")
    public String postRegister() {

        return "/material/list";
    }

    /**
     * 그룹 목록
     */
    @GetMapping("/grouplist")
    public void groupList() {}

    /**
     * 그룹 목록에서 등록 버튼 클릭 시 완료 후 목록 화면으로
     */
    @PostMapping("/groupregister")
    public String GroupRegister() {

        return "/material/grouplist";
    }

    /**
     * 창고 목록
     */
    @GetMapping("/warehouselist")
    public void wareHouseList() {}

    /**
     * 창고 목록에서 등록 버튼 클릭 시 완료 후 목록 화면으로
     */
    @PostMapping("/warehouseregister")
    public String wareHouseRegister() {

        return "/material/warehouselist";
    }

    /**
     * 조달 계획 목록
     */
    @GetMapping("/prcrlist")
    public String prcrList() {

        return "/material/prcrlist";
    }

    /**
     * 조달 계획 목록 수정
     */
    @GetMapping("/prcrupdate")
    public void getPrcrUpdate() {}

    /**
     * 조달 계획 수정 화면에서 저장 버튼 클릭 시 완료 후 조달 계획 목록 화면으로
     */
    @PostMapping("/prcrupdate")
    public String postPrcrUpdate() {
        return "/material/prcrlist";
    }


}
