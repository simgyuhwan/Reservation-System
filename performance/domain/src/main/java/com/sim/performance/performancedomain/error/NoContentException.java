package com.sim.performance.performancedomain.error;

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
    private Long memberId;

    public NoContentException(ErrorMessage errorMessage, Long memberId) {
        super(errorMessage.getMessage() + memberId);
        this.memberId = memberId;
    }

}
