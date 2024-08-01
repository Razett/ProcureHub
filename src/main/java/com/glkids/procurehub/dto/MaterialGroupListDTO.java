package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>자재 그룹이 포함된 목록을 포함하는 목록</p>
 *
 * <p>{@code List<MaterialGroupDTO> materialGroupLists} - 그룹 목록의 목록</p>
 * <p>{@code String target} - 각 단계 별, 최종 그룹을 포함하는 그룹 코드</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MaterialGroupListDTO {

    private List<MaterialGroupDTO> materialGroupLists = new ArrayList<>();
    private String target;
}
