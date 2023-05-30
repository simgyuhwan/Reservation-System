package com.sim.reservation.data.reservation.error;

import lombok.Getter;

@Getter
public class IrrevocableException extends RuntimeException{
	private final Long id;

	public IrrevocableException(ErrorMessage message, Long id) {
		super(message.getMessage() + id);
		this.id = id;
	}
}
