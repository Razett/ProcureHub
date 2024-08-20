package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrcrStatus {
    OK("정상"),
    AUTO_GENERATED("자동 생성됨"),
    AUTO_MODIFIED("자동 수정됨"),
    MODIFIED("수정됨"),
    RED("긴급"),
    YELLOW("경고"),
    ORDER_ADDED("발주 추가됨"),
    RED_ORDER_ADDED("긴급(발주 추가됨)"),
    YELLOW_ORDER_ADDED("경고(발주 추가됨)"),
    ORDERED("발주 진행중"),
    EXPIRED("만료됨");

    private final String value;
}
