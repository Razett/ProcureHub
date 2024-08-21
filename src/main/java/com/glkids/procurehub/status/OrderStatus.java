package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    AUTO_GENERATED("자동 생성됨"),
    AUTO_MODIFIED("자동 수정됨"),
    MODIFIED("수정됨"),
    CONTINUING("진행중"),
    NEEDS_INSPECTION("검수전"),
    INSPECTING("검수중"),
    INSPECTING_WITH_FAILED("검수 불량"),
    OK("정상"),
    RETURNED("반려");

    private final String value;
}
