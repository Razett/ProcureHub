package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractorDTO {

    private Long corno; //사업자 등록번호
    private String name; //회사명
    private String phone; //전화번호
    private String address1; //도로명 주소
    private String address2; //상세 주소
    private String mngrName; //담당자 성명
    private String mngrPhone; //연락처
    private String mngrAddress; //이메일
    private String bank; //은행명
    private String accountNum; //계좌번호

}
