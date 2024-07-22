package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>견적</b>
 *
 * <p>{@code Long qtno} - 견적 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Contractor contractor} - 협력 회사 [FK, Not Null]</p>
 * <p>{@code Emp emp} - 등록 사원 [FK, Not Null]</p>
 * <p>{@code String title} - 견적 제목 [Varchar(255), Not Null]</p>
 * <p>{@code String content} - 견적 내용 [Varchar(1024), Nullable]</p>
 * <p>{@code Integer status} - 견적 상태 코드 [INT, Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Quotation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qtno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Contractor contractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Emp emp;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 1024, nullable = true)
    private String content;

    @Column(nullable = false)
    private Integer status;
}
