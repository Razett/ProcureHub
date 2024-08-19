package com.glkids.procurehub.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrdcMtrlDetailsDTO {
    private Long mtrlno;
    private String name;
    private String standard;
    private Long quantity;
    private Long procureQuantity;
}
