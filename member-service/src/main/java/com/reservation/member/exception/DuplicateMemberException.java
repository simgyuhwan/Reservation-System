package com.reservation.member.exception;

import lombok.Getter;

/**
 * DupplicationMemberException.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
        super();
    }

    public DuplicateMemberException(String message) {
        super(message);
    }
}
