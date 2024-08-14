package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuotationStatus {

    NEED_AGREEMENT("계약 필요"),
    NEED_MATERIAL("자재 필요"),
    AGREEMENT("계약 완료"), // 계약 완료
    DUPPLICATED_MTRL("계약 중복"),
    EXPIRED("계약 만료"),
    CANCELLED("취소됨"),
    CONTINUING("협의중");


    private final String value;

}
