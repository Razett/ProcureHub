package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>조달 계획</b>
 *
 * <p>{@code Long prcrno} - 조달 계획 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Emp emp} - 조달 계획 수정 사원 [FK, Nullable]</p>
 * <p>{@code Material material} - 조달 자재 [FK, Not Null]</p>
 * <p>{@code PrdcPlan prdcPlan} - 생산 계획 [FK, Not Null]</p>
 * <p>{@code LocalDateTime reqdate} - 납기일 [DATETIME, Not Null]</p>
 * <p>{@code Long quantity} - 조달 수량 [BIGINT, Not Null]</p>
 * <p>{@code Integer status} - 조달 상태 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Setter
public class Prcr extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prcrno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Emp emp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PrdcPlan prdcPlan;

    @Column(nullable = false)
    private LocalDateTime reqdate;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Integer status;

    }

