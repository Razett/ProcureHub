package com.glkids.procurehub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>출고</b>
 *
 * <p>{@code Long exportno} - 출고 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Emp emp} - 출고 요청 사원 [FK, Not Null]</p>
 * <p>{@code Prcr prcr} - 조달계획 [FK, Nullable]</p>
 * <p>{@code Long quantity} - 출고요청수량 [BIGINT, Not Null]</p>
 * <p>{@code Emp shipper} - 출고처리사원 [FK, Nullable]</p>
 * <p>{@code LocalDateTime shippeddate} - 출고일 [DATETIME, Nullable]</p>
 * <p>{@code LocalDateTime duedate} - 출고예정일 [DATETIME, Not Null]</p>
 * <p>{@code Integer status} - 출고상태코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Setter
public class Export extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exportno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Emp emp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Prcr prcr;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Emp shipper;

    @Column(nullable = true)
    private LocalDateTime shippeddate;

    @Column(nullable = false)
    private LocalDateTime duedate;

    @Column(nullable = false)
    private Integer status;

}
