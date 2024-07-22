package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <b>입고 검수</b>
 *
 * <p>{@code Long importNspcNo} - 입고 검수 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Import anImport} - 입고 [FK, Not Null]</p>
 * <p>{@code LocalDateTime duedate} - 검수 (예정)일 [DATETIME, Not Null]</p>
 * <p>{@code String content} - 검수 내용 [Varchar(1024), Nullable]</p>
 * <p>{@code Integer dfcQuantity} - 불량 자재 수 [INT, Not Null]</p>
 * <p>{@code Emp emp} - 검수 처리자 [FK, Nullable]</p>
 * <p>{@code Integer status} - 검수 상태 코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class ImportInspection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long importNspcNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Import anImport;

    @Column(nullable = false)
    private LocalDateTime duedate;

    @Column(length = 1024, nullable = true)
    private String content;

    @Column(nullable = false)
    private Integer dfcQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Emp emp;

    @Column(nullable = false)
    private Integer status;
}
