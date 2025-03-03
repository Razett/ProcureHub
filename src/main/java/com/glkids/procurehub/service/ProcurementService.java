package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.PrdcMtrlDetailsDTO;
import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.OrderRepository;
import com.glkids.procurehub.repository.PrcrRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.status.OrderStatus;
import com.glkids.procurehub.status.PrcrStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
        List<Prcr> procurementPlans = prcrRepository.findAll(
                Sort.by(
                        Sort.Order.asc("reqdate"),
                        Sort.Order.asc("prcrno"),
                        Sort.Order.asc("prdcPlan")
                )
        );
        // prdcPlanNo를 기준으로 DTO와 자재 리스트를 관리할 맵
        Map<Long, ProcurementDetailsDTO> dtoMap = new HashMap<>();
        Map<Long, Long> mtrlQuantityMap = new HashMap<>();  // key = mtrlno, value=currentQuantity - prcr.quantity

        for (Prcr prcr : procurementPlans) {
            PrdcPlan prdcPlan = prcr.getPrdcPlan();
            Prdc product = prdcPlan.getPrdc();
            Material material = prcr.getMaterial();


            if (!mtrlQuantityMap.containsKey(material.getMtrlno())) {
                mtrlQuantityMap.put(material.getMtrlno(), material.getQuantity());
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

            mtrlQuantityMap.put(material.getMtrlno(), mtrlQuantityMap.get(material.getMtrlno()) - prcr.getQuantity() < 0 ? 0L : mtrlQuantityMap.get(material.getMtrlno()) - prcr.getQuantity());

            // Order에 자동 추가
            if (mtrlDto.getProcureQuantity() > 0 && prcr.getStatus() < PrcrStatus.ORDERED.ordinal()) {
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
                    if(order.getQuantity().longValue() != mtrlDto.getProcureQuantity()) {
                        order.setStatus(OrderStatus.AUTO_MODIFIED.ordinal());
                    }
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

            if (prcr.getStatus() < 8) {
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
            }

            // 자재 리스트에 자재 추가 (중복 방지)
            if (dto.getMaterials().stream().noneMatch(existing -> existing.getMtrlno().equals(mtrlDto.getMtrlno()))) {
                dto.getMaterials().add(mtrlDto);
            }
        }

        // DTO 리스트 생성 후 납기일(reqdate) 기준으로 정렬
        List<ProcurementDetailsDTO> sortedDtoList = new ArrayList<>(dtoMap.values());
        sortedDtoList.sort(Comparator.comparing(ProcurementDetailsDTO::getReqdate));
        // 최종 DTO 리스트를 생성
        return sortedDtoList;
    }

    @Transactional
    public void changeStatus(Long prcrno , PrcrStatus prcrStatus){
        prcrRepository.changeStatus(prcrno , prcrStatus.ordinal());
    }

    // 긴급 상태 (RED 및 RED_ORDER_ADDED)의 prcr 개수 반환
    @Transactional
    public long countRedStatus() {
        return prcrRepository.countByStatusIn(Arrays.asList(
                PrcrStatus.RED.ordinal()
        ));
    }

    // 경고 상태 (YELLOW 및 YELLOW_ORDER_ADDED)의 prcr 개수 반환
    @Transactional
    public long countYellowStatus() {
        return prcrRepository.countByStatusIn(Arrays.asList(
                PrcrStatus.YELLOW.ordinal()
        ));
    }

    @Transactional
    public long countBlueStatus() {
        return prcrRepository.countByStatusIn(Arrays.asList(
                PrcrStatus.AUTO_MODIFIED.ordinal()
        ));
    }

//    // 만료되지 않은 모든 상태의 prcr 개수 반환 (EXPIRED 상태 제외)
//    @Transactional
//    public long countNonExpiredStatus() {
//        return prcrRepository.countByStatusNot(PrcrStatus.EXPIRED.ordinal());
//    }

    // 전체 조달계획의 개수 반환
    @Transactional
    public long countTotalProcurementPlans() {
        return prcrRepository.count();
    }
}


