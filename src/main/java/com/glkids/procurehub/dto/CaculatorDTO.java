package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.QuotationMtrl;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class CaculatorDTO {

    private QuotationMtrl quotationMtrl;
}
