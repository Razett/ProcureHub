package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.PrdcMtrlDetailsDTO;
import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.OrderRepository;
import com.glkids.procurehub.repository.PrcrRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.status.PrcrStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProcurementService {

    private final PrcrRepository prcrRepository;
    private final QuotationMtrlRepository quotationMtrlRepository;
    private final OrderRepository orderRepository;

    @Transactional
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

            // 각 자재에 대한 quantity를 리스트에 추가
            dto.getProductMtrlQuantity().add(prcr.getQuantity());

            // PrdcMtrlDetailsDTO 생성
            PrdcMtrlDetailsDTO mtrlDto = PrdcMtrlDetailsDTO.builder()
                    .mtrlno(material.getMtrlno())
                    .name(material.getName())
                    .standard(material.getStandard())
                    .status(prcr.getStatus())
                    .quantity(mtrlQuantityMap.get(material.getMtrlno())) // 각 자재에 해당하는 prcr의 quantity 설정
                    // 앞선 조달 계획 처리 후 남는 재고수량과 조달 수량을 비교하여 발주 필요수량을 계산
                    .procureQuantity(mtrlQuantityMap.get(material.getMtrlno()) >= prcr.getQuantity() ? 0 : prcr.getQuantity() - mtrlQuantityMap.get(material.getMtrlno()) + 5)
                    .build();

            // Order에 자동 추가
            if (mtrlDto.getProcureQuantity() > 0) {
                // orderRepository를 통해서 order 테이블에 prcrno가 일치하는 행이 있는지 먼저 확인한다
                Order order = orderRepository.findByPrcr(prcr.getPrcrno());

                // select * from order where prcr_prcrno = :prcrno; --> Order객체를 반환 --> 없으면  Order객체를 만들고, 있으면 반환한 Order 객체에 수량은 값은 다시 세팅
                if (order == null) {
                    order = new Order();
                    order.setPrcr(prcr);
                    order.setMaterial(material); // material 필드 설정 왜 why? Order엔티티에서 material 필드가 not null 임 그래서 material 를 설정해줌
                    order.setQuantity(mtrlDto.getProcureQuantity());
                    order.setStatus(0);
                } else {
                    // 기존 Order 객체의 수량 업데이트
                    order.setQuantity(mtrlDto.getProcureQuantity());
                    order.setMaterial(material); // material 필드 설정
                }

                // if else 끝나고 나면 해당 객체를 공통으로 save돌려버리기  --> order의 status code가 < 3 일때만 save. --> 해당 prcrno의 status를 6번으로 바꿔주기
                if (order.getStatus() < 3) {
                    orderRepository.save(order);
                }

                prcr.setStatus(PrcrStatus.ORDER_ADDED.ordinal());
                mtrlDto.setStatus(PrcrStatus.ORDER_ADDED.ordinal());
                prcrRepository.save(prcr);
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
            }
            if (leadtime != -1) {
                mtrlDto.setLeadtime(leadtime);
            }

            // LT + Buffer 가 현재시간과 얼마나 차이나는지에 따라 컬러값 세팅.
            LocalDate now = LocalDate.now();
            LocalDate reqdate = prcr.getReqdate().toLocalDate();

            if (leadtime <= 0) {
                leadtime = 7;
            }

            if (now.plusDays(leadtime + 2 + 3).isAfter(reqdate) || now.plusDays(leadtime + 2 + 3).isEqual(reqdate)) {
                if (prcr.getStatus() == PrcrStatus.ORDER_ADDED.ordinal()) {
                    mtrlDto.setStatus(PrcrStatus.RED_ORDER_ADDED.ordinal());
                    changeStatus(prcr.getPrcrno(), PrcrStatus.RED_ORDER_ADDED); // DB 에다가 status 바꿔버리는 prcrRepository.changeStatus(dto.getPrcrno, PrcrStatus.RED) 만들고 돌려버리기
                } else {
                    mtrlDto.setStatus(PrcrStatus.RED.ordinal());
                    changeStatus(prcr.getPrcrno(), PrcrStatus.RED); // DB 에다가 status 바꿔버리는 prcrRepository.changeStatus(dto.getPrcrno, PrcrStatus.RED) 만들고 돌려버리기
                }
            } else if (now.plusDays(leadtime + 2 + 5).isAfter(reqdate) || now.plusDays(leadtime + 2 + 5).isEqual(reqdate)) {
                if (prcr.getStatus() == PrcrStatus.ORDER_ADDED.ordinal()) {
                    mtrlDto.setStatus(PrcrStatus.YELLOW_ORDER_ADDED.ordinal());
                    changeStatus(prcr.getPrcrno(), PrcrStatus.YELLOW_ORDER_ADDED);
                } else {
                    mtrlDto.setStatus(PrcrStatus.YELLOW.ordinal());
                    changeStatus(prcr.getPrcrno(), PrcrStatus.YELLOW);
                }
            }

            // 자재 리스트에 자재 추가 (중복 방지)
            if (dto.getMaterials().stream().noneMatch(existing -> existing.getMtrlno().equals(mtrlDto.getMtrlno()))) {
                dto.getMaterials().add(mtrlDto);
            }
        }

        // 최종 DTO 리스트를 생성
        return new ArrayList<>(dtoMap.values());
    }

    @Transactional
    public void changeStatus(Long prcrno , PrcrStatus prcrStatus){
        prcrRepository.changeStatus(prcrno , prcrStatus.ordinal());
    }

}


