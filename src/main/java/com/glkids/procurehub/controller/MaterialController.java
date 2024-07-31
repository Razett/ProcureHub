package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.MaterialGroupDTO;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.MaterialWarehouse;
import com.glkids.procurehub.repository.MaterialGroupRepository;
import com.glkids.procurehub.repository.MaterialWarehouseRepository;
import com.glkids.procurehub.service.MaterialService;
import com.glkids.procurehub.service.MaterialServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 자재 관리 메뉴 컨트롤러
 */
@RequiredArgsConstructor
@RequestMapping("/material")
@Controller
public class MaterialController {

    private final MaterialService materialService;

    /**
     * 자재 목록
     */
    @GetMapping("/list")
    public String List(Material material, Model model) {

        model.addAttribute("materiallist", materialService.list());
        model.addAttribute("warehouses" , materialService.getWarehouses());
        return "/material/list";
    }

    /**
     * 자재상세보기 화면
     */
    @GetMapping("/read")
    public String read(Long mtrlno, Model model) {

        MaterialDTO materialDTO = materialService.read(mtrlno);
        model.addAttribute("material", materialDTO);
        model.addAttribute("materialGroupDirection", materialService.getMaterialGroupDirection(materialDTO.getMaterialGroup()));
        model.addAttribute("materialFile", null);
        return "material/read";
    }

    /**
     * 자재 수정
     */
    @GetMapping("/update")
    public String getUpdate(Model model, Long mtrlno) {

        MaterialDTO dto = materialService.read(mtrlno);
        model.addAttribute("material", dto);
        model.addAttribute("materialGroupList", materialService.getMaterialGroupLists(dto.getMaterialGroup()));
        model.addAttribute("warehouses", materialService.getWarehouses());

        return "/material/update";
    }

    /**
     * 자재 수정 처리
     */
    @PostMapping("/update")
    public String postUpdate(@ModelAttribute MaterialDTO materialDTO, Model model, @RequestParam("wrhscode") MaterialWarehouse materialWarehouse, @RequestParam("grpcode") MaterialGroup materialGroup) {

        materialDTO.setMaterialGroup(materialGroup);
        materialDTO.setMaterialWarehouse(materialWarehouse);
        materialDTO.setStatus(0);
        System.out.println(materialDTO);
        materialService.update(materialDTO);

        return "redirect:read?mtrlno=" + materialDTO.getMtrlno();
    }

    @PostMapping("/delete")
    public String delete(Long mtrlno) {
        materialService.delete(mtrlno);
        return "redirect:material/list" ;
    }

    /**
     * 자재 등록
     */
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("warehouses",materialService.getWarehouses());
        model.addAttribute("topMaterialGroups", materialService.getMaterialGroupsByDepth(0));
        return "material/register";
    }

    /**
     * 자재 등록 버튼 클릭 시 목록 화면으로
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute MaterialDTO materialDTO, @RequestParam("wrhscode") MaterialWarehouse materialWarehouse, @RequestParam("grpcode") MaterialGroup materialGroup, Model model) {

        System.out.println(materialGroup);
        materialDTO.setMaterialGroup(materialGroup);
        materialDTO.setMaterialWarehouse(materialWarehouse);
        materialDTO.setStatus(0);
        model.addAttribute("regist", materialService.register(materialDTO).getMtrlno());

        return "/material/list";
    }


    /**
     * 그룹 목록
     */
    @GetMapping("/grouplist")
    public void groupList() {}

    /**
     * @deprecated
     * 그룹 목록에서 등록 버튼 클릭 시 완료 후 목록 화면으로
     */
    @Deprecated
    @PostMapping("/groupregister")
    public String GroupRegister() {

        return "/material/grouplist";
    }

    /**
     * 창고 목록
     */
    @GetMapping("/warehouselist")
    public void warehouseList(Model model , MaterialWarehouse materialWarehouse) {
        model.addAttribute("warehouses", materialService.getWarehouses());
    }

    /**
     * @deprecated
     * 창고 목록에서 등록 버튼 클릭 시 완료 후 목록 화면으로
     */
    @Deprecated
    @PostMapping("/warehouseregister")
    public String warehouseRegister() {

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
     * @deprecated
     * 조달 계획 목록 수정
     */
    @Deprecated
    @GetMapping("/prcrupdate")
    public void getPrcrUpdate() {}

    /**
     * @deprecated
     * 조달 계획 수정 화면에서 저장 버튼 클릭 시 완료 후 조달 계획 목록 화면으로
     */
    @Deprecated
    @PostMapping("/prcrupdate")
    public String postPrcrUpdate() {
        return "/material/prcrlist";
    }




}
