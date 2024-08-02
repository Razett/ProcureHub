package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Quotation;

import java.time.LocalDateTime;

public class AgreementDTO {

    private Long grmno;
    private Contractor contractor; //협력 회사
    private Quotation quotation ;// 견적
    private Emp emp; // 등록 사원
    private String title;// 계약 제목
    private String content;  //세부 내용
    private LocalDateTime startdate; //시작일
    LocalDateTime enddate ; //만료일 [DATETIME, Not Null]</p>
    Integer status; // 계약 상태 [INT, Not Null]</p>
}
