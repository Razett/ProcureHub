package com.glkids.procurehub.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>AGREEMENT(0) - 계약 완료</p>
 * <p>DUPPLICATED_MTRL(1) - 동일 회사, 동일 자재 계약 완료 건 존재</p>
 * <p>NEED_AGREEMENT(2) - 계약 필요</p>
 * <p>EXPIRED(3) - 만료됨</p>
 * <p>CANCELLED(4) - 취소됨</p>
 * <p>CONTINUING(5) - 협상 진행 중</p>
 */
@Getter
@RequiredArgsConstructor
public enum QuotationStatus {

    AGREEMENT(0), // 계약 완료
    DUPPLICATED_MTRL(1),
    NEED_AGREEMENT(2),
    EXPIRED(3),
    CANCELLED(4),
    CONTINUING(5);


    private final int value;

}
