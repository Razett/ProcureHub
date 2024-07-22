package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>견적 자재</b>
 *
 * <p>{@code Long qtmtno} - 견적 자재 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Quotation quotation} - 견적 [FK, Not Null]</p>
 * <p>{@code Emp emp} - 등록 사원 [FK, Not Null]</p>
 * <p>{@code Material material} - 자재 [FK, Not Null]</p>
 * <p>{@code Long quantity} - 월 납품 수량 [BIGINT, Not Null]</p>
 * <p>{@code Integer unitprice} - 단가 [INT, Not Null]</p>
 * <p>{@code Integer totalprice} - 총 금액 [INT, Not Null]</p>
 * <p>{@code Integer leadtime} - 리드타임(일) [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class QuotationMtrl extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qtmtno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Quotation quotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Emp emp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Material material;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Integer unitprice;

    @Column(nullable = false)
    private Integer totalprice;

    @Column(nullable = false)
    private Integer leadtime;
}
