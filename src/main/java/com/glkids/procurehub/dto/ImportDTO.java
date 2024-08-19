package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.status.ImportStatus;
import com.glkids.procurehub.status.PrcrStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * <b>입고</b>
 *
 * <p>{@code Long importno} - 입고 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Order order} - 발주 [FK, Not Null]</p>
 * <p>{@code Long quantity} - 입고 수량 [BIGINT, Not Null]</p>
 * <p>{@code LocalDateTime arrivaldate} - 자재 수령일 [DATETIME, Nullable]</p>
 * <p>{@code Emp receiver} - 자재 수령 확인자 [FK, Nullable]</p>
 * <p>{@code LocalDateTime approvedate} - 입고 처리 날짜 [DATETIME, Nullable]</p>
 * <p>{@code Emp approver} - 입고 처리자 [FK, Nullable]</p>
 * <p>{@code Integer status} - 입고 상태 코드 [INT, Not Null]</p>
 */


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ImportDTO {
    private Long importno;
    private Order order;
    private Long quantity;
    private LocalDateTime arrivaldate;
    private Emp receiver;
    private LocalDateTime approvedate;
    private Emp approver;
    private Integer status;

    private Contractor contractor;

    public String getStatusString() {
        if (status != null) {
            for (ImportStatus importStatus : ImportStatus.values()) {
                if (importStatus.ordinal() == status) {
                    return importStatus.getValue();
                }
            }
        }
        return null;
    }
}
