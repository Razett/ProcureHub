package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InspectionStatus {
    NOT_YET("검수 예정"),
    OK("정상"),
    DFC("검수 불량"), // 입고
    FAILED("검수 불량"),
    CANCELLED("취소됨");

    private final String value;
}
