package com.glkids.procurehub.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

/**
 * <b>자재 그룹</b>
 *
 * <p>{@code String grpcode} - 그룹 코드 [Varchar(30), PK, Not Null]</p>
 * <p>{@code String pGrpcode} - 상위 그룹 코드 [Varchar(30), Nullable]</p>
 * <p>{@code Integer depth} - 단계 [INT, Not Null]</p>
 * <p>{@code String name} - 그룹명 [Varchar(100), Not Null]</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MaterialGroupDTO {

    private String grpcode; //그룹 코드
    private String pGrpcode; //상위 그룹 코드
    private Integer depth; //단계
    private String name; //그룹명
}
