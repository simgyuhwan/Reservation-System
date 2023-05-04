package com.reservation.memberservice.error;

import com.reservation.common.error.ErrorMessage;

/**
 * InvalidUserIdException.java
 * 유효하지 않은 회원 ID 예외
 *
 * @author sgh
 * @since 2023.05.04
 */
public class InvalidUserIdException extends RuntimeException{
    private String userId;

    public InvalidUserIdException(ErrorMessage message, String userId) {
        super(message.name() + userId);
        this.userId = userId;
    }
}
