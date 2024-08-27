package com.glkids.procurehub.service;


import com.glkids.procurehub.dto.PrdcPlanDTO;
import com.glkids.procurehub.entity.Prdc;
import com.glkids.procurehub.entity.PrdcPlan;
import com.glkids.procurehub.repository.PrdcPlanRepository;
import com.glkids.procurehub.repository.PrdcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class PrdcPlanServiceImpl implements PrdcPlanService {

    private final PrdcPlanRepository prdcPlanRepository;
    private final PrdcRepository prdcRepository;

    @Override
    public List<PrdcPlanDTO> list() {
        List<PrdcPlan> prdcplanDTOList = prdcPlanRepository.findAll();
        List<PrdcPlanDTO> prdcDTOList = new ArrayList<>();
        prdcplanDTOList.forEach(x -> prdcDTOList.add(PrdcPlanEntityToDTO(x)));
        return prdcDTOList;
    }

    @Override
    public PrdcPlan savePrdcPlan(PrdcPlanDTO prdcPlanDTO) {
        Prdc prdc = prdcRepository.findById(prdcPlanDTO.getPrdcPlanNo())
                .orElseThrow(() -> new RuntimeException("Prdc not found"));

        PrdcPlan prdcPlan = PrdcPlan.builder()
                .prdc(prdc)
                .quantity(prdcPlanDTO.getQuantity())
                .startdate(prdcPlanDTO.getStartdate())
                .enddate(prdcPlanDTO.getEnddate())
                .build();

        return prdcPlanRepository.save(prdcPlan);
    }

    @Override
    public Prdc getPrdcByNo(Long prdcno) {
        return prdcRepository.findById(prdcno)
                .orElseThrow(() -> new IllegalArgumentException("Invalid prdcNo: " + prdcno));
    }

    @Override
    public List<Prdc> getAllprdc() {
        return prdcRepository.findAll();
    }

    @Override
    public PrdcPlan createPrdcPlan(PrdcPlanDTO prdcPlanDTO) {
        Prdc prdc = prdcRepository.findById(prdcPlanDTO.getPrdcNo())
                .orElseThrow(() -> new RuntimeException("자재를 찾을 수 없습니다: " + prdcPlanDTO.getPrdcNo()));

        PrdcPlan prdcPlan = PrdcPlan.builder()
                .prdc(prdc)
                .quantity(prdcPlanDTO.getQuantity())
                .startdate(prdcPlanDTO.getStartdate())
                .enddate(prdcPlanDTO.getEnddate())
                .build();

        return prdcPlanRepository.save(prdcPlan);
    }

    @Override
    public List<Map<String, Object>> getTotalQuantityByPrdcNameForMonth(LocalDateTime startDate, LocalDateTime endDate) {
        return prdcPlanRepository.findTotalQuantityByPrdcNameBetweenDates(startDate,endDate);
    }

    public Long updatePrdcPlan (PrdcPlanDTO prdcPlanDTO) {
      return (long) prdcPlanRepository.updateQuantityByPrdcPlanNo(prdcPlanDTO.getQuantity(), prdcPlanDTO.getPrdcPlanNo());
    }
}