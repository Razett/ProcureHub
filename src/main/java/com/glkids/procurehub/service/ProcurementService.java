package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.PrdcMtrlDetailsDTO;
import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.PrcrRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.status.PrcrStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcurementService {

    private final PrcrRepository prcrRepository;

    private final QuotationMtrlRepository quotationMtrlRepository;
//    public List<ProcurementDetailsDTO> getProcurementDetails() {
//          return prcrRepository.findAllProcurements();
//    }

    public List<ProcurementDetailsDTO> getProcurementDetailsGroupMtrl() {
        List<Prcr> procurementPlans = prcrRepository.findAll();

        // prdcPlanNo를 기준으로 DTO와 자재 리스트를 관리할 맵
        Map<Long, ProcurementDetailsDTO> dtoMap = new HashMap<>();
        Map<Long, Long> mtrlQuantityMap = new HashMap<>();  // key = mtrlno, value=currentQuantity - prcr.quantity

        for (Prcr prcr : procurementPlans) {
            PrdcPlan prdcPlan = prcr.getPrdcPlan();
            Prdc product = prdcPlan.getPrdc();
            Material material = prcr.getMaterial();


            if (!mtrlQuantityMap.containsKey(material.getMtrlno())) {
                mtrlQuantityMap.put(material.getMtrlno(), material.getQuantity());
            } else {
                mtrlQuantityMap.put(material.getMtrlno(), mtrlQuantityMap.get(material.getMtrlno()) - prcr.getQuantity() < 0 ? 0L : mtrlQuantityMap.get(material.getMtrlno()) - prcr.getQuantity());
            }


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

            // LT 최대값 구하기
            List<QuotationMtrl> qmList = quotationMtrlRepository.findByMaterial(material.getMtrlno());

            int leadtime = -1;
            if (!qmList.isEmpty()) {

                for(QuotationMtrl qm : qmList) {
                    if (qm.getLeadtime() > leadtime) {
                        leadtime = qm.getLeadtime();
                    }
                }

                if (leadtime != -1) {
                    dto.setLeadtime(leadtime);
                }
            }

            if (leadtime != -1) {
                LocalDate now = LocalDate.now();
                LocalDate reqdate = dto.getReqdate().toLocalDate();

                if (now.plusDays(leadtime + 2 + 3).isAfter(reqdate) || now.plusDays(leadtime + 2 + 3).isEqual(reqdate)) {
                    dto.setLeadtimeColor(0);
                } else if (now.plusDays(leadtime + 2 + 5).isAfter(reqdate) || now.plusDays(leadtime + 2 + 5).isEqual(reqdate)) {
                    dto.setLeadtimeColor(1);
                } else {
                    dto.setLeadtimeColor(3);
                }
            }

            if (dto.getStatus() == PrcrStatus.AUTO_MODIFIED.ordinal())
                dto.setLeadtimeColor(2);
            // LT + Buffer 가 현재시간과 얼마나 차이나는지에 따라 컬러값 세팅.

            // 각 자재에 대한 quantity를 리스트에 추가
            dto.getProductMtrlQuantity().add(prcr.getQuantity());

            // PrdcMtrlDetailsDTO 생성
            PrdcMtrlDetailsDTO mtrlDto = PrdcMtrlDetailsDTO.builder()
                    .mtrlno(material.getMtrlno())
                    .name(material.getName())
                    .standard(material.getStandard())
                    .quantity(mtrlQuantityMap.get(material.getMtrlno())) // 각 자재에 해당하는 prcr의 quantity 설정
                    // 앞선 조달 계획 처리 후 남는 재고수량과 조달 수량을 비교하여 발주 필요수량을 계산
                    .procureQuantity(mtrlQuantityMap.get(material.getMtrlno()) >= prcr.getQuantity() ? 0 : prcr.getQuantity() - mtrlQuantityMap.get(material.getMtrlno()) + 5)
                    .build();

            // Order에 자동 추가
            if (mtrlDto.getProcureQuantity() > 0) {
                // orderRepository를 통해서 order 테이블에 prcrno가 일치하는 행이 있는지 먼저 확인한다
                // select * from order where prcr_prcrno = :prcrno; --> Order객체를 반환 --> 없으면  Order객체를 만들고, 있으면 반환한 Order 객체에 수량은 값은 다시 세팅
                // if else 끝나고 나면 해당 객체를 공통으로 save돌려버리기  --> order의 status code가 < 3 일때만 save. --> 해당 prcrno의 status를 1번으로 바꿔주기
                System.out.println("gigi");
            }

            // 자재 리스트에 자재 추가 (중복 방지)
            if (dto.getMaterials().stream().noneMatch(existing -> existing.getMtrlno().equals(mtrlDto.getMtrlno()))) {
                dto.getMaterials().add(mtrlDto);
            }
        }

        // 최종 DTO 리스트를 생성
        return new ArrayList<>(dtoMap.values());
    }
}


