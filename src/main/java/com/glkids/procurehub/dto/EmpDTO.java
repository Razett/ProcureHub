package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>사원</b>
 *
 * <p>{@code Long empno} - 사원번호 [BIGINT, PK, Not Null]
 * <p>{@code String name} - 사원이름 [Varchar(12), Not Null]
 * <p>{@code String pw} - 암호 [Varchar(20), Not Null]
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpDTO {

    private Long empno; //사업자 등록번호
    private String name; //담당자 성명
    private String pw; //암호
}
