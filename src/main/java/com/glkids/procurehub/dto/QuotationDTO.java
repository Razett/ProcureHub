package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.status.QuotationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>견적</b>
 *
 * <p>{@code Long qtno} - 견적 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Contractor contractor} - 협력 회사 [FK, Not Null]</p>
 * <p>{@code Emp emp} - 등록 사원 [FK, Not Null]</p>
 * <p>{@code String title} - 견적 제목 [Varchar(255), Not Null]</p>
 * <p>{@code String content} - 견적 내용 [Varchar(1024), Nullable]</p>
 * <p>{@code Integer status} - 견적 상태 코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuotationDTO {

    private Long qtno; //견적 코드
    private Contractor contractor; //협력 회사
    private Emp emp; //등록 사원
    private String title; //견적 제목
    private String content; //견적 내용
    private Integer status; //견적 상태 코드
    private LocalDateTime moddate; // 수정날짜
    private LocalDateTime regdate; // 추가날짜
    @Builder.Default
    private List<QuotationMtrlDTO> quotationMtrlList = new ArrayList<>();
    private Long agreementCount; // 계약 개수
    private Long quotationMtrlCount;
    private Long corno;
    private QuotationStatus quotationStatus;

    public String getQuotationStatusString() {
        if (status != null) {
            for (QuotationStatus quotationStatus : QuotationStatus.values()) {
                if (quotationStatus.ordinal() == status) {
                    return quotationStatus.getValue();
                }
            }
        }
        return null;
    }
}
