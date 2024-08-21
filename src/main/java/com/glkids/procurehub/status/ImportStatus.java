package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImportStatus {
    AUTO_GENERATED("자동 생성됨"),
    AUTO_MODIFIED("자동 수정됨"),
    MODIFIED("수정됨"),
    CONTINUING("입고 진행중"),
    NEEDS_INSPECTION("검수 전"),
    INSPECTING("검수 중"),
    INSPECTING_WITH_FAILED("검수 실패"),
    OK("정상"),
    RETURNED("반품됨");

    private final String value;
}
