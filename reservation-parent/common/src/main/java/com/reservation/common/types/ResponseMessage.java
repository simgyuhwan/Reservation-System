package com.reservation.common.types;

import lombok.Getter;

/**
 * ResponseMessage.java
 * 공통 응답 메시지
 *
 * @author sgh
 * @since 2023.05.10
 */
@Getter
public enum ResponseMessage {
    // performance
    PERFORMANCE_CREATED_REQUEST_COMPLETE("공연 등록 요청이 완료되었습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
}
