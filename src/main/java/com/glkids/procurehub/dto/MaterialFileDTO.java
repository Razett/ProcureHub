package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MaterialFileDTO {

    private Long mtrlfno;
    private Material material;
    private String uuid;
    private String name;
    private String url;
    private Long mtrlno;
}
