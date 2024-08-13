package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.PrcrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcurementService {

    @Autowired
    private PrcrRepository prcrRepository;

    public List<ProcurementDetailsDTO> getProcurementDetailsByStatus() {
          return prcrRepository.findAllProcurements();
    }
}
    //쿼리 dsl  빌더로 해서 짜는법
//    public List<ProcurementDetailsDTO> getProcurementDetails() {
//        List<Prcr> procurementPlans = prcrRepository.findAll();
//
//        return procurementPlans.stream().map(prcr -> {
//            PrdcPlan prdcPlan = prcr.getPrdcPlan();  // 조달 계획에서 연관된 생산 계획 가져오기
//            Prdc product = prdcPlan.getPrdc();  // 생산 계획에서 연관된 제품 가져오기
//            Material material = prcr.getMaterial();  // 조달 계획에서 연관된 자재 가져오기
//
//            return ProcurementDetailsDTO.builder()
//                    .prcrno(prcr.getPrcrno())  // 조달 계획 코드
//                    .reqdate(prcr.getReqdate())  // 납기일
//                    .prdcPlanNo(prdcPlan.getPrdcPlanNo())  // 생산 계획 코드
//                    .startdate(prdcPlan.getStartdate())  // 생산 시작일
//                    .prdcno(product.getPrdcno())  // 생산 제품 코드
//                    .productName(product.getName())  // 생산 제품명
//                    .productQuantity(prdcPlan.getQuantity())  // 생산 제품 수량
//                    .mtrlno(material.getMtrlno())  // 자재 번호
//                    .materialName(material.getName())  // 자재명
//                    .standard(material.getStandard())  // 자재 표준
//                    .materialQuantity(material.getQuantity())  // 자재 수량
//                    .materialProcurementQuantity(prcr.getQuantity())  // 조달 자재 수량
//                    .status(prcr.getStatus())  // 조달 상태
//                    .build();
//        }).collect(Collectors.toList());
//    }

