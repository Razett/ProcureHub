package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpCountDTO {
    private Long empno;          // 사원 번호
    private String name;         // 사원 이름

    private Long importCount;     // 입고 일일 처리 건수

    private Long exportCount; // 발주 일일 처리 건 수

    private Long orderCount;// 출고 일일 처리 건수

    private Long quotationCount;// 견적 일일 처리 건 수


    // 일일 건 수
    public EmpCountDTO(Long empno, String name, int importCount, int exportCount, int orderCount, int quotationCount) {
            this.empno = empno;
            this.name = name;
            this.importCount = (long) importCount;
            this.exportCount = (long) exportCount;
            this.orderCount = (long) orderCount;
        this.quotationCount = (long) quotationCount;
    }
}
