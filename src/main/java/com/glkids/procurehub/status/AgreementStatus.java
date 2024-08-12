package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgreementStatus {
    AGREEMENT("계약 완료"),
    NEED_MATERIAL("자재 필요"),
    DUPPLICATED_MTRL("자재 중복"),
    EXPIRED("만료됨"),
    CANCELLED("취소됨"),
    CONTINUING("협의중");



    private final String value;
}
