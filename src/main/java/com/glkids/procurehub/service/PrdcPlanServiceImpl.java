package com.glkids.procurehub.service;


import com.glkids.procurehub.dto.PrdcPlanDTO;
import com.glkids.procurehub.entity.PrdcPlan;
import com.glkids.procurehub.repository.PrdcPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PrdcPlanServiceImpl implements PrdcPlanService {

    private final PrdcPlanRepository prdcPlanRepository;

    @Override
    public List<PrdcPlanDTO> list() {
        List<PrdcPlan> prdcplanDTOList = prdcPlanRepository.findAll();
        List<PrdcPlanDTO> prdcDTOList = new ArrayList<>();
        prdcplanDTOList.forEach(x -> prdcDTOList.add(PrdcPlanEntityToDTO(x)));
        return prdcDTOList;
    }

}
