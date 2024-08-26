package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * <b>부서</b>
 *
 * <p>{@code Long deptno} - 부서번호 [BIGINT, PK, Not Null]
 * <p>{@code String name} - 부서명 [Varchar(255), Not Null]
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Dept extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptno;

    @Column(length = 255, nullable = false)
    private String name;
}
