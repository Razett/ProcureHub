package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.PrdcMtrlDetailsDTO;
import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.PrcrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcurementService {

    @Autowired
    private PrcrRepository prcrRepository;

//    public List<ProcurementDetailsDTO> getProcurementDetails() {
//          return prcrRepository.findAllProcurements();
//    }

    public List<ProcurementDetailsDTO> getProcurementDetailsGroupMtrl() {
        List<Prcr> procurementPlans = prcrRepository.findAll();

        // prdcPlanNo를 기준으로 DTO와 자재 리스트를 관리할 맵
        Map<Long, ProcurementDetailsDTO> dtoMap = new HashMap<>();

        for (Prcr prcr : procurementPlans) {
            PrdcPlan prdcPlan = prcr.getPrdcPlan();
            Prdc product = prdcPlan.getPrdc();
            Material material = prcr.getMaterial();

            Long prdcPlanNo = prdcPlan.getPrdcPlanNo();

            // DTO 가져오거나 새로 생성
            ProcurementDetailsDTO dto = dtoMap.get(prdcPlanNo);
            if (dto == null) {
                // 새로운 DTO 생성하여 맵에 추가
                dto = ProcurementDetailsDTO.builder()
                        .prcrno(prcr.getPrcrno())
                        .prdcPlanNo(prdcPlanNo)
                        .reqdate(prcr.getReqdate())
                        .startdate(prdcPlan.getStartdate())
                        .prdcno(product.getPrdcno())
                        .productName(product.getName())
                        .productQuantity(prdcPlan.getQuantity())
                        .productMtrlQuantity(new ArrayList<>()) // 빈 리스트로 초기화
                        .status(prcr.getStatus())
                        .regdate(prcr.getRegdate())
                        .moddate(prcr.getModdate())
                        .materials(new ArrayList<>())  // 빈 자재 리스트 생성
                        .build();
                dtoMap.put(prdcPlanNo, dto);
            }

            // 각 자재에 대한 quantity를 리스트에 추가
            dto.getProductMtrlQuantity().add(prcr.getQuantity());

            // PrdcMtrlDetailsDTO 생성
            PrdcMtrlDetailsDTO mtrlDto = PrdcMtrlDetailsDTO.builder()
                    .mtrlno(material.getMtrlno())
                    .name(material.getName())
                    .standard(material.getStandard())
                    .quantity(material.getQuantity()) // 각 자재에 해당하는 prcr의 quantity 설정
                    .procureQuantity(material.getQuantity() >= prcr.getQuantity() ? 0 : prcr.getQuantity() - material.getQuantity() + 5) // 로직 수정
                    .build();

            // 자재 리스트에 자재 추가 (중복 방지)
            if (dto.getMaterials().stream().noneMatch(existing -> existing.getMtrlno().equals(mtrlDto.getMtrlno()))) {
                dto.getMaterials().add(mtrlDto);
            }
        }

        // 최종 DTO 리스트를 생성
        return new ArrayList<>(dtoMap.values());
    }
}


