package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>자재 그룹</b>
 *
 * <p>{@code String grpcode} - 그룹 코드 [Varchar(30), PK, Not Null]</p>
 * <p>{@code String name} - 그룹명 [Varchar(100), Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class MaterialGroup {

    @Id
    @Column(length = 30, nullable = false)
    private String grpcode;

    @Column(length = 100, nullable = false)
    private String name;
}
