package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Emp extends BaseEntity {

    // 사원번호 (Primary Key)
    @Id
    private Long empno;

    // 이름
    @Column(length = 255, nullable = false)
    private String name;

    // 부서
    @ManyToOne(fetch = FetchType.LAZY)
    private Dept dept;

    // 암호
    @Column(length = 255, nullable = false)
    private String pw;
}
