package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class Dept extends BaseEntity {

    // 부서 번호 (Primary Key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptno;

    // 부서 명
    @Column(length = 255, nullable = false)
    private String name;
}
