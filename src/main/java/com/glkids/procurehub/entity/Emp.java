package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>사원</b>
 *
 * <p>{@code Long empno} - 사원번호 [BIGINT, PK, Not Null]
 * <p>{@code String name} - 사원이름 [Varchar(12), Not Null]
 * <p>{@code Dept dept} - 소속부서 [FK]
 * <p>{@code String pw} - 암호 [Varchar(20), Not Null]
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Emp extends BaseEntity {

    @Id
    private Long empno;

    @Column(length = 12, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Dept dept;

    @Column(length = 20, nullable = false)
    private String pw;
}
