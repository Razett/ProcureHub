package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PrdcDTO {
    private Long prdcno;
    private String name;
    private List<Integer> quantity; // 제품 수량 필드 추가
    private List<String> materialNames;

}
