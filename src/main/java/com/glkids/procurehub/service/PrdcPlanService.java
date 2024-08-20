package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.PrcrDTO;
import com.glkids.procurehub.dto.PrdcDTO;
import com.glkids.procurehub.dto.PrdcPlanDTO;
import com.glkids.procurehub.entity.Calendar;
import com.glkids.procurehub.entity.Prcr;
import com.glkids.procurehub.entity.Prdc;
import com.glkids.procurehub.entity.PrdcPlan;

import java.util.List;

public interface PrdcPlanService {
    //1. 업체 목록
    List<PrdcPlanDTO> list();

    PrdcPlan savePrdcPlan(PrdcPlanDTO prdcPlanDTO);

    Prdc getPrdcByNo(Long prdcno);

    List<Prdc> getAllprdc();

    PrdcPlan createPrdcPlan(PrdcPlanDTO prdcPlanDTO);


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

