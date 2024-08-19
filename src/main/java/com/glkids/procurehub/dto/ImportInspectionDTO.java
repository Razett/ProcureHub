package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Imports;
import com.glkids.procurehub.status.ImportStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <b>입고 검수</b>
 *
 * <p>{@code Long importNspcNo} - 입고 검수 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Import anImport} - 입고 [FK, Not Null]</p>
 * <p>{@code LocalDateTime duedate} - 검수 (예정)일 [DATETIME, Not Null]</p>
 * <p>{@code String content} - 검수 내용 [Varchar(1024), Nullable]</p>
 * <p>{@code Integer dfcQuantity} - 불량 자재 수 [INT, Not Null]</p>
 * <p>{@code Emp emp} - 검수 처리자 [FK, Nullable]</p>
 * <p>{@code Integer status} - 검수 상태 코드 [INT, Not Null]</p>
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportInspectionDTO {
    private Long importNspcNo;
    private Imports imports;
    private LocalDateTime duedate;
    private String content;
    private Integer dfcQuantity;
    private Emp emp;
    private Integer status;

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
