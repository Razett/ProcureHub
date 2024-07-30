package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>협력 업체</b>
 *
 * <p>{@code Long corno} - 사업자 등록 번호 [BIGINT, PK, Not Null]</p>
 * <p>{@code String name} - 회사명 [Varchar(150), Not Null]</p>
 * <p>{@code String phone} - 내선번호 [Varchar(11), Not Null]</p>
 * <p>{@code address1} - 기본주소 [Varchar(100), Not Null]</p>
 * <p>{@code address2} - 상세주소 [Varchar(100), Nullable]</p>
 * <p>{@code mngrName} - 담당자 이름 [Varchar(12), Nullable]</p>
 * <p>{@code mngrPhone} - 담당자 전화번호 [Varchar(11) Nullable]</p>
 * <p>{@code mngrAddress} - 담당자 이메일 [Varchar(50) Nullable]</p>
 * <p>{@code bank} - 은행 명 [Varchar(30), Nullable]</p>
 * <p>{@code accountNum} - 계좌 번호 [Varchar(20), Nullable]</p>
 */
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
