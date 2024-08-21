package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>발주 진척 검수</b>
 *
 * <p>{@code Integer status} - 검수 상태 코드(검수 현황)[INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderInspectionDTO {

    private Integer status;
}
