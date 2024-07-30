package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

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
 * <p>{@code bank} - 은행명 [Varchar(30), Nullable]</p>
 * <p>{@code accountNum} - 계좌 번호 [Varchar(20), Nullable]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Contractor extends BaseEntity{

    @Id
    private Long corno;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column(length = 100, nullable = false)
    private String address1;

    @Column(length = 100, nullable = true)
    private String address2;

    @Column(length = 12, nullable = true)
    private String mngrName;

    @Column(length = 11, nullable = true)
    private String mngrPhone;

    @Column(length = 50, nullable = true)
    private String mngrAddress;

    @Column(length = 30, nullable = true)
    private String bank;

    @Column(length = 20, nullable = true)
    private String accountNum;
}
