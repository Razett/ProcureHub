package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>발주</b>
 *
 * <p>{@code Long orderno} - 발주 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Emp emp} - 발주 실행 사원 [FK, Nullable]</p>
 * <p>{@code LocalDateTime orderdate} - 발주 실행일 [DATETIME, Not Null]</p>
 * <p>{@code Prcr prcr} - 조달 계획 [FK, Nullable]</p>
 * <p>{@code Material material} - 발주할 자재 [FK, Not Null]</p>
 * <p>{@code QuotationMtrl quotationmtrl} - 견적에서 선택한 자재 [FK, Nullable]</p>
 * <p>{@code Long quantity} - 발주 수량 [BIGINT, Not Null]</p>
 * <p>{@code String trackingNo} - 운송장 번호 [Varchar(20), Nullable]</p>
 * <p>{@code Integer status} - 발주 상태 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "`Order`")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Emp emp;

    @Column(nullable = true)
    private LocalDateTime orderdate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Prcr prcr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private QuotationMtrl quotationmtrl;

    @Column(nullable = false)
    private Long quantity;

    @Column(length = 20, nullable = true)
    private String trackingNo;

    @Column(nullable = false)
    private Integer status;
}
