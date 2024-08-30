package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Prdc;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class PrdcMtrlDTO {
    private Long prdcMtrlNo;

    private Prdc prdc;

    private Material material;

    private Integer quantity;

}
