package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImportStatus {
    AUTO_GENERATED("Auto Generated"),
    AUTO_MODIFIED("Auto Modified"),
    MODIFIED("Modified"),
    CONTINUING("Continuing"),
    NEEDS_INSPECTION("Needs Inspection"),
    INSPECTING("Inspecting"),
    INSPECTING_WITH_FAILED("Inspecting"),
    OK("정상"),
    RETURNED("");

    private final String value;
}
