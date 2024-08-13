package com.glkids.procurehub.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
public class ProcurementDetailsDTO  {

    private Long prcrNo;  // 조달 계획 코드
    private LocalDateTime reqDate;  // 납기일
    private Long prdcPlanNo;  // 생산 계획 코드
    private LocalDateTime startDate;  // 생산 시작일
    private Long prdcNo;  // 생산 제품 코드
    private String productName;  // 생산 제품명
    private Long productQuantity;  // 생산 제품 수량
    private Long mtrlno;  // 자재 번호
    private String materialName;  // 자재명
    private String materialStandard;  // 자재 표준
    private Long materialQuantity;  // 자재 수량
    private Long materialProcurementQuantity;  // 조달 자재 수량
    private Integer status;  // 조달 상태
    private Integer leadtime;  // 리드타임 추가
    private LocalDateTime regdate;  // 생성 일자
    private LocalDateTime moddate;  // 수정 일자
}
