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

    public List<ProcurementDetailsDTO> getProcurementDetails() {
        List<Prcr> procurementPlans = prcrRepository.findAll();

        return procurementPlans.stream().map(prcr -> {
            PrdcPlan prdcPlan = prcr.getPrdcPlan();
            Prdc product = prdcPlan.getPrdc();
            Material material = prcr.getMaterial();
            QuotationMtrl quotationMtrl = prcr.getQuotationMtrl(); // QuotationMtrl 객체 가져오기

            return ProcurementDetailsDTO.builder()
                    .prcrNo(prcr.getPrcrno())
                    .reqDate(prcr.getReqdate())
                    .prdcPlanNo(prdcPlan.getPrdcPlanNo())
                    .startDate(prdcPlan.getStartdate())
                    .prdcNo(product.getPrdcno())
                    .productName(product.getName())
                    .productQuantity(prdcPlan.getQuantity())
                    .mtrlno(material.getMtrlno())
                    .materialName(material.getName())
                    .materialStandard(material.getStandard())
                    .materialQuantity(material.getQuantity())
                    .materialProcurementQuantity(prcr.getQuantity())
                    .status(prcr.getStatus())
                    .regdate(prcr.getRegdate())
                    .moddate(prcr.getModdate())
                    .build();
        }).collect(Collectors.toList());
    }
}
