package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>발주 진척 검수</b>
 *
 * <p>{@code Long nspcno} - 진척 검수 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Order order} - 발주 [FK, Not Null]</p>
 * <p>{@code LocalDateTime duedate} - 검수(예정)일 [DATETIME, Not Null]</p>
 * <p>{@code String content} - 검수 내용 [Varchar(1024), Not Null]</p>
 * <p>{@code Emp inspector} - 검수자 [FK, Nullable]</p>
 * <p>{@code Integer status} - 검수 상태 코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class OrderInspection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nspcno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(nullable = false)
    private LocalDateTime duedate;

    @Column(length = 1024, nullable = true)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Emp inspector;

    @Column(nullable = false)
    private Integer status;
}
