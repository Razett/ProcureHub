package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Prdc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PrdcPlanDTO {
    private Long prdcPlanNo; //생산 계획 코드
    private Prdc prdc; //제품
    private Long quantity; //생산
    private LocalDateTime startdate; // 생산 시작일
    private LocalDateTime enddate; //생산 종료일
}
