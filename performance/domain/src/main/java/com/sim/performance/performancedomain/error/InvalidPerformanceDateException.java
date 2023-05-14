package com.sim.performance.performancedomain.error;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class InvalidPerformanceDateException extends RuntimeException {
	public InvalidPerformanceDateException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
}
