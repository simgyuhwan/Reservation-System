package com.reservation.common.error;

import lombok.Getter;

@Getter
public class EventNotFound extends RuntimeException{
	private String id;

	public EventNotFound(ErrorMessage message, String id) {
		super(message + id);
		this.id = id;
	}
}
