package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpDTO {

    private Long empno; //사업자 등록번호
    private String name; //담당자 성명
    private String pw; //연락처
}
