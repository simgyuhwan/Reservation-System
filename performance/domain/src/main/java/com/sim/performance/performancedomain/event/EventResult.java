package com.sim.performance.performancedomain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventResult {
	private String id;
	private boolean success;
	private String message;

	@Builder
	public EventResult(String id, boolean success, String message) {
		this.id = id;
		this.success = success;
		this.message = message;
	}

	public static EventResult success(String id) {
		return EventResult.builder()
			.id(id)
			.success(true)
			.build();
	}

	public static EventResult fail(String id, String message) {
		return EventResult.builder()
			.id(id)
			.success(false)
			.message(message)
			.build();
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean isFailure() {
		return !success;
	}

}
