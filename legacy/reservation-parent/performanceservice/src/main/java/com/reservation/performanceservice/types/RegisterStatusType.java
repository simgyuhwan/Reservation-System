package com.reservation.performanceservice.types;

import lombok.Getter;

/**
 * RegisterStatusType.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.11
 */
@Getter
public enum RegisterStatusType {
    PENDING("등록 요청 대기중"),
    COMPLETED("등록 완료"),
    FAILED("등록 실패");

    private final String message;

    RegisterStatusType(String message) {
        this.message = message;
    }
}
