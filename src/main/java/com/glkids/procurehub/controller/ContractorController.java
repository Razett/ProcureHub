package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Quotation;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.service.*;
import com.glkids.procurehub.status.QuotationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


/**
 * 계약 관리 메뉴 컨트롤러
 */
@RequestMapping("/contractor")
@Controller
@RequiredArgsConstructor
public class ContractorController {

    private final ContractorService contractorService;
    private final QuotationService quotationService;
    private final AgreementService agreementservice;

    /**
     * 업체 목록
     */
    @GetMapping("/list")
    public String List(@AuthenticationPrincipal UserDTO userDTO, Model model, ContractorDTO contractorDTO) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "업체 목록");

        model.addAttribute("contractorList", contractorService.list());


        return "/contractor/list";
    }

    /**
     * 업체 상세정보
     *
     * @param corno 사업자 등록 번호
     * @return /contractor/get 을 요청하여 업체 상세페이지를 표시합니다.
     */
    @GetMapping("/read")
    public String read(@AuthenticationPrincipal UserDTO userDTO, Long corno, Integer quotationPage, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "업체 정보");

        model.addAttribute("contractorRead", contractorService.read(corno));
        model.addAttribute("quotationList", contractorService.quoListByContractor(corno, 0));
        return "/contractor/read";
    }

    /**
     * 업체 등록 화면
     */
    @GetMapping("/register")
    public String getRegister(@AuthenticationPrincipal UserDTO userDTO, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "업체 등록");

        return "/contractor/register";
    }

    /**
     * 업체 등록 처리
     */
    @PostMapping("/register")
    public String postRegister(ContractorDTO contractorDTO, RedirectAttributes redirectAttributes) {
        if (contractorService.register(contractorDTO)) {
            redirectAttributes.addFlashAttribute("msg", "업체가 등록되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "업체 등록에 실패하였습니다.");
        }
        return "redirect:/contractor/list";
    }

    /**
     * 업체 수정 화면
     */
    @GetMapping("/update")
    public String getUpdate(@AuthenticationPrincipal UserDTO userDTO, Long corno, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "업체 수정");

        ContractorDTO dto = contractorService.read(corno);
        model.addAttribute("updateread", dto);

        return "/contractor/update";
    }

    /**
     * 업체 수정 처리
     */
    @PostMapping("/update")
    public String postUpdate(@ModelAttribute ContractorDTO contractorDTO, RedirectAttributes redirectAttributes) {
        contractorService.update(contractorDTO);
        redirectAttributes.addFlashAttribute("msg", "업체 정보가 수정되었습니다.");
        return "redirect:/contractor/read?corno=" + contractorDTO.getCorno();
    }

    /**
     * 견적 목록
     */
    @GetMapping("/quolist")
    public String quoList(@AuthenticationPrincipal UserDTO userDTO, String type, String input ,Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "견적 목록");

        model.addAttribute("quotationList", contractorService.quoList(type, input));
        return "/contractor/quolist";
    }

    /**
     * 견적 등록
     */
    @GetMapping("/quoregister")
    public String getQuoRegister(@AuthenticationPrincipal UserDTO userDTO, Long corno, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "견적 등록");
        model.addAttribute("emp" , userDTO.getEmp().getEmpno());
        model.addAttribute("ContractNum", corno);

        return "/contractor/quoregister";
    }

    /**
     * 견적 등록 처리
     */
    @PostMapping("/quoregister")
    public String postQuoRegister(QuotationDTO quotationDTO, RedirectAttributes redirectAttributes) {
        if(contractorService.quoRegister(quotationDTO)) {
            redirectAttributes.addFlashAttribute("msg", "견적이 등록되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "견적이 등록에 실패하였습니다.");
        }
        return "redirect:/contractor/quolist";
    }

    /**
     * 견적 상세보기
     */
    @GetMapping("/quoread")
    public String quodatail(@AuthenticationPrincipal UserDTO userDTO, Long qtno, Model model) {
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "견적 정보");

        QuotationDTO quotationDTO = contractorService.quoread(qtno);
        List<QuotationMtrlDTO> quotationMtrlList = quotationService.readQuotationMtrlList(qtno);
        if (quotationMtrlList.isEmpty() && quotationDTO.getStatus() != QuotationStatus.NEED_MATERIAL.ordinal()) {
            contractorService.quoStatusNoMtrl(qtno);
            quotationDTO.setStatus(QuotationStatus.NEED_MATERIAL.ordinal());
        }
        model.addAttribute("quotation", quotationDTO);
        model.addAttribute("quotationMtrlList", quotationMtrlList);
        model.addAttribute("quoFileList", quotationService.quotationFileList(qtno));
        model.addAttribute("agreementList", agreementservice.readListByQtno(qtno));

        return "/contractor/quoread";
    }

    @GetMapping("/quoupdate")
    public String quoupdate(@AuthenticationPrincipal UserDTO userDTO, Long qtno, Model model){
        model.addAttribute("user", userDTO);
        model.addAttribute("title", "견적 수정");

        QuotationDTO dto = contractorService.quoread(qtno);
        model.addAttribute("updateread", dto);

        model.addAttribute("materialupdate", quotationService.readQuotationMtrlList(qtno));

        return "/contractor/quoupdate";
    }

    @PostMapping("/quoupdate")
    public String Postquoupdate(@ModelAttribute QuotationDTO quotationDTO, RedirectAttributes redirectAttributes){


        quotationDTO.setContractor(Contractor.builder().corno(quotationDTO.getCorno()).build());
        quotationDTO.setEmp(Emp.builder().empno(201758030L).build());
        contractorService.quoupdate(quotationDTO);
        redirectAttributes.addFlashAttribute("msg", "견적이 수정되었습니다.");

        return "redirect:/contractor/quoread?qtno=" + quotationDTO.getQtno();
    }

    @GetMapping("/quotationForm")
    public String quotationForm(@AuthenticationPrincipal UserDTO userDTO, Model model, Long qtno){
        QuotationDTO quotationDTO = contractorService.quoread(qtno);

        if (quotationDTO.getStatus() == QuotationStatus.NEED_AGREEMENT.ordinal()) {
            quotationService.changeStatus(qtno, QuotationStatus.CONTINUING);
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("quotationMtrlList", quotationService.readQuotationMtrlList(qtno));
        model.addAttribute("quotation" , quotationDTO);

        return "/contractor/quotationForm";
    }

}
