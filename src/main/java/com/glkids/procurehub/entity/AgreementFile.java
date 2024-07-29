package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>계약서 파일</b>
 *
 * <p>{@code Long grmfno} - 계약서 파일 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Agreement agreement} - 계약 [FK, Not Null]</p>
 * <p>{@code String uuid} - UUID [Varchar(255), Nullable]</p>
 * <p>{@code String name} - 파일명 [Varchar(255), Not Null]</p>
 * <p>{@code String url} - URL [Varchar(255), Not Null]</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
public class AgreementFile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grmfno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Agreement agreement;

    @Column(nullable = true)
    private String uuid;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String url;
}
