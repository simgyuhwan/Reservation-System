package com.sim.reservation.data.reservation.error;

import lombok.Getter;

@Getter
public class PerformanceScheduleNotFoundException extends RuntimeException{
	private final Long id;

	public PerformanceScheduleNotFoundException(ErrorMessage errorMessage, Long id) {
		super(errorMessage.name() + id);
		this.id = id;
	}
}
