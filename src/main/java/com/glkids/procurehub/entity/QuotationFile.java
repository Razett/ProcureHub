package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>견적서 파일</b>
 *
 * <p>{@code Long qtfno} - 견적서 파일 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Quotation quotation} - 견적 [FK, Not Null]</p>
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
public class QuotationFile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qtfno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Quotation quotation;

    @Column(length = 255, nullable = true)
    private String uuid;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String url;
}
