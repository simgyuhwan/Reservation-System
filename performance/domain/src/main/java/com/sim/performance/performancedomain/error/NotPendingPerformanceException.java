package com.sim.performance.performancedomain.error;

import lombok.Getter;

/**
 * NotPendingPerformanceException.java
 *
 * @author sgh
 * @since 2023.05.11
 */
@Getter
public class NotPendingPerformanceException extends RuntimeException{
	private Long id;

	public NotPendingPerformanceException(ErrorMessage errorMessage, Long performanceId) {
		super(errorMessage.getMessage() + performanceId);
		this.id = performanceId;
	}
}
