package com.sim.reservationservice.error;

import lombok.Getter;

/**
 * SoldOutException.java
 * 공연 매진 예외
 *
 * @author sgh
 * @since 2023.04.26
 */
@Getter
public class SoldOutException extends RuntimeException {
	public SoldOutException() {
		super();
	}

	public SoldOutException(String message) {
		super(message);
	}
}
