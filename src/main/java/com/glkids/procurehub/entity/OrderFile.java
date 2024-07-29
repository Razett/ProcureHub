package com.glkids.procurehub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * <b>출하명세서 파일</b>
 *
 * <p>{@code Long orderfno} - 출하명세서 파일 코드 [BIGINT, PK, Not Null]</p>
 * <p>{@code Order order} - 발주 [FK, Not Null]</p>
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
public class OrderFile extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderfno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;

    @Column(nullable = true)
    private String uuid;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String url;
}
