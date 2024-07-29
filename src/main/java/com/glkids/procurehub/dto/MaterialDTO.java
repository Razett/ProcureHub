package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.MaterialWarehouse;
import jakarta.persistence.*;
import lombok.Data;

/**
 * <b>자재</b>
 *
 * <p>{@code Long mtrlno} - 자재 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code String name} - 자재명 [Varchar(100), Not Null]</p>
 * <p>{@code String description} - 세부 내용 [Varchar(1024), Nullable]</p>
 * <p>{@code String standard} - 규격 [Varchar(60), Not Null]</p>
 * <p>{@code Long quantity} - 수량 [BIGINT, Not Null]</p>
 * <p>{@code MaterialGroup materialGroup} - 자재 그룹 [FK, Not Null]</p>
 * <p>{@code MaterialWarehouse materialWarehouse} - 자재 창고 위치 [FK, Not Null]</p>
 * <p>{@code Integer status} - 자재 상태 코드 [INT, Not Null]</p>
 */

@Data
public class MaterialDTO {
    private Long mtrlno;
    private String name;
    private String description;
    private String standard;
    private Long quantity;
    private MaterialGroup materialGroup;
    private MaterialWarehouse materialWarehouse;
    private Integer status;
}
