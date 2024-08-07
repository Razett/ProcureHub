package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.ContractorDTO;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.repository.ContractorRepository;
import com.glkids.procurehub.service.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST 컨트롤러 클래스
 */
@RestController
@RequiredArgsConstructor
public class ContractorRestController {

    private final ContractorService contractorService;
    private final ContractorRepository contractorRepository;

    /**
     * 회사명을 통해 회사 정보를 가져오는 메서드
     * @param contractorName 회사명
     * @return 회사 정보 DTO
     */

    @GetMapping("/contractor/findByNameContaining")
    public List<ContractorDTO> searchContractorByName(@RequestParam("name") String name) {
        System.out.println(contractorService.findByNameContaining(name));
        return contractorService.findByNameContaining(name);

    }


    // Long 값을 문자열로 변환하여 부분 일치를 찾는 엔드포인트
    @GetMapping("/contractor/findByCornoContaining")
    public List<Contractor> findByCornoContaining(@RequestParam("corno") String corno) {
        return contractorRepository.findByCornoContaining(corno);
    }
}
