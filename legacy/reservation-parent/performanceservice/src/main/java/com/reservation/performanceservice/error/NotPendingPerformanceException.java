package com.reservation.performanceservice.error;

import com.reservation.common.error.ErrorMessage;

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
