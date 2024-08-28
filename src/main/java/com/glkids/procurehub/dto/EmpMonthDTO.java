package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpMonthDTO {
    private Long empno;          // 사원 번호
    private String name;         // 사원 이름

    private Long importMonth;

    private Long exportMonth;

    private Long orderMonth;

    private Long quotationMonth;// 견적 일일 처리 건 수

    // 월별 건 수
    public EmpMonthDTO(Long empno, String name, int importMonth, int exportMonth, int orderMonth, int quotationMonth){
        this.empno = empno;
        this.name = name;
        this.importMonth = (long) importMonth;
        this.exportMonth = (long) exportMonth;
        this.orderMonth = (long) orderMonth;
        this.quotationMonth = (long) quotationMonth;
    }
}
