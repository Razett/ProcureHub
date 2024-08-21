package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Prcr;
import com.glkids.procurehub.status.ExportStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>출고</b>
 *
 * <p>{@code Long exportno} - 출고 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Emp emp} - 출고 요청 사원 [FK, Not Null]</p>
 * <p>{@code Prcr prcr} - 조달계획 [FK, Nullable]</p>
 * <p>{@code Long quantity} - 출고요청수량 [BIGINT, Not Null]</p>
 * <p>{@code Emp shipper} - 출고처리사원 [FK, Nullable]</p>
 * <p>{@code LocalDateTime shippeddate} - 출고일 [DATETIME, Nullable]</p>
 * <p>{@code LocalDateTime duedate} - 출고예정일 [DATETIME, Not Null]</p>
 * <p>{@code Integer status} - 출고상태코드 [INT, Not Null]</p>
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Setter
public class ExportDTO {

    private Long exportno;
    private Emp emp;
    private Prcr prcr;
    private Long quantity;
    private Emp shipper;
    private LocalDateTime shippeddate;
    private LocalDateTime duedate;
    private LocalDateTime registerdate;
    private LocalDateTime moddate;
    private Integer status;

    public String getStatusString() {
        if (status != null) {
            for (ExportStatus exportStatus : ExportStatus.values()) {
                if (exportStatus.ordinal() == status) {
                    return exportStatus.getValue();
                }
            }
        }
        return null;
    }

    public boolean getIsValidateExcute() {
        return this.prcr.getMaterial().getQuantity() >= this.quantity;
    }
}
