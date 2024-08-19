package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.PrdcPlan;
import com.glkids.procurehub.status.ImportStatus;
import com.glkids.procurehub.status.PrcrStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcurementDetailsDTO  {

    private Long prcrno;  // 조달 계획 코드
    private LocalDateTime reqdate;  // 납기일
    private Long prdcPlanNo;  // 생산 계획 코드
    private LocalDateTime startdate;  // 생산 시작일
    private Long prdcno;  // 생산 제품 코드
    private String productName;  // 생산 제품명
    private Long productQuantity;
    private List<Long> productMtrlQuantity; // 생산 필요 자재 갯수
    private Integer status;  // 조달 상태
    private LocalDateTime regdate;  // 생성 일자
    private LocalDateTime moddate;  // 수정 일자
    private List<PrdcMtrlDetailsDTO> materials;  // 자재 리스트
    private Integer leadtime;
    private Integer leadtimeColor;

    public String getStatusString() {
        if (status != null) {
            for (PrcrStatus prcrStatus : PrcrStatus.values()) {
                if (prcrStatus.ordinal() == status) {
                    return prcrStatus.getValue();
                }
            }
        }
        return null;
    }
}
