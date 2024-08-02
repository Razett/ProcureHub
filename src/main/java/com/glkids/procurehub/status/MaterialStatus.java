package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialStatus {
    OK(0),
    EXPIRED(1);

    private final int value;
}
