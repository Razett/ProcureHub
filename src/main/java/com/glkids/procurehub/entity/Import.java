package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>입고</b>
 *
 * <p>{@code Long importno} - 입고 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Order order} - 발주 [FK, Not Null]</p>
 * <p>{@code Long quantity} - 입고 수량 [BIGINT, Not Null]</p>
 * <p>{@code LocalDateTime arrivaldate} - 자재 수령일 [DATETIME, Nullable]</p>
 * <p>{@code Emp receiver} - 자재 수령 확인자 [FK, Nullable]</p>
 * <p>{@code LocalDateTime approvedate} - 입고 처리 날짜 [DATETIME, Nullable]</p>
 * <p>{@code Emp approver} - 입고 처리자 [FK, Nullable]</p>
 * <p>{@code Integer status} - 입고 상태 코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Import extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long importno;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = true)
    private LocalDateTime arrivaldate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Emp receiver;

    @Column(nullable = true)
    private LocalDateTime approvedate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Emp approver;

    @Column(nullable = false)
    private Integer status;

}
