package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>입고 출하 명세서</b>
 *
 *  <p>{@code Long importfno} - 파일 코드 [BIGINT, PK, Not Null]</p>
 *  <p>{@code Import anImport} - 입고 [FK, Not Null]</p>
 *  <p>{@code String uuid} - UUID [Varchar(255), Nullable]</p>
 *  <p>{@code String name} - 파일명 [Varchar(150), Not Null]</p>
 *  <p>{@code String url} - URL [Varchar(255), Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class ImportFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long importfno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Imports imports;

    @Column(nullable = true)
    private String uuid;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String url;
}
