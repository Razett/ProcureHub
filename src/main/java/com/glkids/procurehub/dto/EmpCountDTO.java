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
    private Long importCount;     // 입고 처리 건수
    private Long exportCount;
    private Long orderCount;// 출고 처리 건수
    private Long quotationCount;

    // 필요한 경우, int 타입을 사용하는 생성자 추가 (기존)
    public EmpCountDTO(Long empno, String name, int importCount, int exportCount, int orderCount, int quotationCount) {
        this.empno = empno;
        this.name = name;
        this.importCount = (long) importCount;
        this.exportCount = (long) exportCount;
        this.orderCount = (long) orderCount;
        this.quotationCount = (long) quotationCount;
    }
}
