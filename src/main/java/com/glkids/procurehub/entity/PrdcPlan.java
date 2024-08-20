package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>제품 생산 계획</b>
 *
 * <p>{@code Long prdcPlanNo} - 생산 계획 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Prdc prdc} - 제품 [FK, Not Null]</p>
 * <p>{@code Long quantity} - 생산 제품 수량 [BIGINT, Not Null]</p>
 * <p>{@code LocalDateTime startdate} - 생산 시작일 [DATETIME, Not Null]</p>
 * <p>{@code LocalDateTime enddate} - 생산 종료일 [DATETIME. Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class PrdcPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prdcPlanNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Prdc prdc;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private LocalDateTime startdate;

    @Column(nullable = false)
    private LocalDateTime enddate;

}
