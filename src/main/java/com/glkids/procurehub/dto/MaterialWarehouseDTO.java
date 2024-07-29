package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>자재 창고</b>
 *
 * <p>{@code String wrhscode} - 창고 코드 [Varchar(30), PK, Not Null]</p>
 * <p>{@code String name} - 창고명 [Varchar(100), Not Null]</p>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialWarehouseDTO {
    private String wrhscode;
    private String wrhsname;
}
