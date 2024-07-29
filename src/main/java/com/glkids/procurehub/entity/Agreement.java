package com.glkids.procurehub.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>계약</b>
 *
 * <p>{@code Long grmno} - 계약 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Contractor contractor} - 협력 회사 [FK, Not Null]</p>
 * <p>{@code Quotation quotation} - 견적 [FK, Not Null]</p>
 * <p>{@code Emp emp} - 등록 사원 [FK, Not Null]</p>
 * <p>{@code String title} - 계약 제목 [Varchar(255), Not Null]</p>
 * <p>{@code String content} - 세부 내용 [Varchar(1024), Not Null]</p>
 * <p>{@code LocalDateTime startdate} - 시작일 [DATETIME, Not Null]</p>
 * <p>{@code LocalDateTime enddate} - 만료일 [DATETIME, Not Null]</p>
 * <p>{@code Integer status} - 계약 상태 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Agreement extends BaseEntity {

    /**
     * 계약 코드 [BIGINT, PK, Not Null]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grmno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Contractor contractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Quotation quotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Emp emp;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true, length = 1024)
    private String content;

    @Column(nullable = false)
    private LocalDateTime startdate;

    @Column(nullable = false)
    private LocalDateTime enddate;

    @Column(nullable = false)
    private Integer status;

}