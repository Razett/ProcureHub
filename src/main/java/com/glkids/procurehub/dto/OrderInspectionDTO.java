package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.status.InspectionStatus;
import com.glkids.procurehub.status.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <b>발주 진척 검수</b>
 *
 * <p>{@code Long nspcno} - 진척 검수 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Order order} - 발주 [FK, Not Null]</p>
 * <p>{@code LocalDateTime duedate} - 검수(예정)일 [DATETIME, Not Null]</p>
 * <p>{@code String content} - 검수 내용 [Varchar(1024), Not Null]</p>
 * <p>{@code Emp inspector} - 검수자 [FK, Nullable]</p>
 * <p>{@code Integer status} - 검수 상태 코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderInspectionDTO {

    private Long nspcno;
    private Order order;
    private LocalDateTime duedate;
    private String content;
    private Emp inspector;
    private Integer status;

    private String duedateString;
    private Long orderno;

    public String getStatusString() {
        if (status != null) {
            for (InspectionStatus inspectionStatus : InspectionStatus.values()) {
                if (inspectionStatus.ordinal() == status) {
                    return inspectionStatus.getValue();
                }
            }
        }
        return null;
    }
}
