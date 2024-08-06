package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Quotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationMtrlDTO {

    private Long qtmtno;
    private Long quotationId;  // Quotation 엔티티의 ID
    private Quotation quotation;
    private Long materialId;   // Material 엔티티의 ID
    private Material material;
    private Long quantity;
    private Long emp;
    private Integer unitprice;
    private Integer totalprice;
    private Integer leadtime;
}
