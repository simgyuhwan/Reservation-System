package com.reservation.performanceservice.error;

import lombok.Getter;

/**
 * NoContentException.java
 * 등록한 컨텐츠가 없을 때, 발생
 *
 * @author sgh
 * @since 2023.04.06
 */
@Getter
public class NoContentException extends RuntimeException{
    private String userId;

    public NoContentException() {
        super();
    }

    public NoContentException(String userId) {
        super();
        this.userId = userId;
    }
}
