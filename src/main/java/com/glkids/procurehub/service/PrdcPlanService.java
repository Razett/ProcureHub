package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.PrdcPlanDTO;
import com.glkids.procurehub.entity.PrdcPlan;

import java.util.List;

public interface PrdcPlanService {
    //1. 업체 목록
    List<PrdcPlanDTO> list();

    default PrdcPlanDTO PrdcPlanEntityToDTO(PrdcPlan entity) {
        PrdcPlanDTO prdcPlanDTO = PrdcPlanDTO.builder()
                .prdcPlanNo(entity.getPrdcPlanNo())
                .quantity(entity.getQuantity())
                .startdate(entity.getStartdate())
                .enddate(entity.getEnddate())
                .build();
        return prdcPlanDTO;
    }
}

