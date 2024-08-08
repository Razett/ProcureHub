package com.glkids.procurehub.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class QuotationFileDTO {
    private String name;
    private String url;
    private String uuid;
    private Long qtno;
    private Long qtfno;
}