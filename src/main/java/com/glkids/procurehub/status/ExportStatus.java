package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExportStatus {
    AUTO_GENERATED("자동 생성됨"),
    AUTO_MODIFIED("자동 수정됨"),
    MODIFIED("수정됨"),
    CONTINUING("진행 중"),
    OK("완료"),
    CANCELED("취소됨");

    private final String value;
}
