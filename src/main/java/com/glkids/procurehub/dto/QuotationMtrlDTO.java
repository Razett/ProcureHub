package com.glkids.procurehub.dto;

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
    private Long empId;        // Emp 엔티티의 ID
    private Long materialId;   // Material 엔티티의 ID
    private Long quantity;
    private Integer unitprice;
    private Integer totalprice;
    private Integer leadtime;
}
