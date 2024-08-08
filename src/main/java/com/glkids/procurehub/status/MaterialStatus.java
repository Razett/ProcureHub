package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialStatus {
    OK("정상"),
    EXPIRED("만료됨");

    private final String value;
}
