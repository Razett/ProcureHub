package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Quotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementDTO {

    private Long grmno;
    private Contractor contractor; //회사
    private Quotation quotation ;// 견적
    private Emp emp; // 등록 사원
    private String title;// 계약 제목
    private String content;  //세부 내용
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startdate; //시작일
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime enddate ; //만료일 [DATETIME, Not Null]</p>
    private Integer status; // 계약 상태 [INT, Not Null]</p>
    private LocalDateTime regdate;
    private LocalDateTime modedate;
    private Long corno;
    private Long qtno;

}
