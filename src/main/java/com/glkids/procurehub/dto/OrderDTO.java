package com.glkids.procurehub.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.status.AgreementStatus;
import com.glkids.procurehub.status.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <b>발주</b>
 *
 * <p>{@code Long orderno} - 발주 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Emp emp} - 발주 실행 사원 [FK, Nullable]</p>
 * <p>{@code LocalDateTime orderdate} - 발주 실행일 [DATETIME, Not Null]</p>
 * <p>{@code Prcr prcr} - 조달 계획 [FK, Nullable]</p>
 * <p>{@code Material material} - 발주할 자재 [FK, Not Null]</p>
 * <p>{@code QuotationMtrl quotationmtrl} - 견적에서 선택한 자재 [FK, Nullable]</p>
 * <p>{@code Long quantity} - 발주 수량 [BIGINT, Not Null]</p>
 * <p>{@code String trackingNo} - 운송장 번호 [Varchar(20), Nullable]</p>
 * <p>{@code Integer status} - 발주 상태 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDTO {

    private Long orderno; //발주 코드
    private Emp emp; //발주 실행 사원
    private LocalDateTime orderdate; //발주 실행일
    private Prcr prcr; //조달 계획
    private Material material; //발주할 자재
    private QuotationMtrl quotationmtrl; //견적에서 선택한 자재
    private Long quantity; //발주 수량
    private Long orderQuantity; //발주 수량
    private String trackingNo; //운송장 번호
    private Integer status; //발주 상태
    private LocalDateTime regdate; //DB에 생성된 날짜
    private LocalDateTime moddate; //마지막 수정된 날짜
    private Long qtmtno;
    private List<QuotationMtrl> quotationmtrlList;

    public String getStatusString() {
        if (status != null) {
            for (OrderStatus orderStatus : OrderStatus.values()) {
                if (orderStatus.ordinal() == status) {
                    return orderStatus.getValue();
                }
            }
        }
        return null;
    }

    public Integer getIndex() {
        int i = 0;

        if (this.quotationmtrl != null && !quotationmtrlList.isEmpty()) {
            for (QuotationMtrl quotationMtrl : quotationmtrlList) {
                if (quotationmtrl.getQtmtno().longValue() == quotationMtrl.getQtmtno().longValue()) {
                    return i;
                }
                i++;
            }
        } else {
            return 0;
        }

        return 0;
    }

}
