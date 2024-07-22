package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>제품 자재</b>
 *
 * <p>{@code Long prdcMtrlNo} - 제품 자재 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Prdc prdc} - 제품 [FK, Not Null]</p>
 * <p>{@code Material material} - 소요 자재 [FK, Not Null]</p>
 * <p>{@code Integer quantity} - 소요 자재 수량 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class PrdcMtrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prdcMtrlNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Prdc prdc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Material material;

    @Column(nullable = false)
    private Integer quantity;
}
