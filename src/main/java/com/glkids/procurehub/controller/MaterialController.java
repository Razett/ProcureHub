package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.MaterialWarehouse;
import com.glkids.procurehub.service.MaterialService;
import com.glkids.procurehub.service.PrdcService;
import com.glkids.procurehub.service.PrdcServiceImpl;
import com.glkids.procurehub.service.ProcurementService;
import com.glkids.procurehub.status.MaterialStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 자재 관리 메뉴 컨트롤러
 */
@RequiredArgsConstructor
@RequestMapping("/material")
@Controller
public class MaterialController {

    private final MaterialService materialService;
    private final ProcurementService procurementService;
    private final PrdcServiceImpl materialServiceImpl;
    private final PrdcService prdcService;

    /**
     * 자재 목록
     */
    @GetMapping("/list")
    public String List(@AuthenticationPrincipal UserDTO userDTO, String inputSearch, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "자재 목록");

        model.addAttribute("materiallist", materialService.list(inputSearch));
        return "/material/list";
    }

    /**
     * 자재상세보기 화면
     */
    @GetMapping("/read")
    public String read(@AuthenticationPrincipal UserDTO userDTO, Long mtrlno, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "자재 정보");

        MaterialDTO materialDTO = materialService.read(mtrlno);
        model.addAttribute("material", materialDTO);
        model.addAttribute("materialGroupDirection", materialService.getMaterialGroupDirection(materialDTO.getMaterialGroup()));
        model.addAttribute("materialFileList", materialService.materialFileList(mtrlno));
        model.addAttribute("prdcMtrlList", prdcService.getPrdcmtrlByMtrl(mtrlno));
        return "/material/read";
    }

    /**
     * 자재 수정
     */
    @GetMapping("/update")
    public String getUpdate(@AuthenticationPrincipal UserDTO userDTO, Model model, Long mtrlno) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "자재 수정");

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
    public String postUpdate(@ModelAttribute MaterialDTO materialDTO, Model model, @RequestParam("wrhscode") MaterialWarehouse materialWarehouse, @RequestParam("grpcode") MaterialGroup materialGroup, RedirectAttributes redirectAttributes) {

        materialDTO.setMaterialGroup(materialGroup);
        materialDTO.setMaterialWarehouse(materialWarehouse);
        materialDTO.setStatus(MaterialStatus.OK);
        System.out.println(materialDTO);
        materialService.update(materialDTO);
        redirectAttributes.addFlashAttribute("msg", "수정되었습니다.");

        return "redirect:/material/read?mtrlno=" + materialDTO.getMtrlno();
    }

    @PostMapping("/delete")
    public String delete(Long mtrlno, RedirectAttributes redirectAttributes) {
        materialService.delete(mtrlno);
        redirectAttributes.addFlashAttribute("msg", "삭제되었습니다.");
        return "redirect:/material/list" ;
    }

    /**
     * 자재 등록
     */
    @GetMapping("/register")
    public String getRegister(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "자재 등록");

        model.addAttribute("warehouses",materialService.getWarehouses());
        model.addAttribute("topMaterialGroups", materialService.getMaterialGroupsByDepth(0));
        return "/material/register";
    }

    /**
     * 자재 등록 버튼 클릭 시 목록 화면으로
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute MaterialDTO materialDTO, @RequestParam("wrhscode") MaterialWarehouse materialWarehouse, @RequestParam("grpcode") MaterialGroup materialGroup, RedirectAttributes redirectAttributes) {

        System.out.println(materialGroup);
        materialDTO.setMaterialGroup(materialGroup);
        materialDTO.setMaterialWarehouse(materialWarehouse);
        materialDTO.setStatus(MaterialStatus.OK);
        Long mtrlno = materialService.register(materialDTO).getMtrlno();

        if (mtrlno != null) {
            redirectAttributes.addFlashAttribute("msg", "등록되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록에 실패하였습니다.");
        }
        return "redirect:/material/list";
    }


    /**
     * 그룹 목록
     */
    @GetMapping("/grouplist")
    public String groupList(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "자재 그룹");

        model.addAttribute("materialGroups", materialService.getAllMaterialGroups());

        return "/material/grouplist";
    }

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
    public String warehouseList(@AuthenticationPrincipal UserDTO userDTO, Model model , MaterialWarehouse materialWarehouse) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "자재 창고");

        model.addAttribute("warehouses", materialService.getWarehouses());

        return "/material/warehouselist";
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
    public String prcrList(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "조달 계획");

        List<ProcurementDetailsDTO> procurementDetails = procurementService.getProcurementDetailsGroupMtrl();
        model.addAttribute("orderList", procurementDetails);
        model.addAttribute("materials", procurementDetails);

        return "/material/prcrlist";  // prcrlist.html 뷰로 이동
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
