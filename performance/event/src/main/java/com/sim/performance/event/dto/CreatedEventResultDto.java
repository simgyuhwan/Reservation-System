package com.sim.performance.event.dto;

import com.sim.performance.event.core.EventResult;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CreatedEventResultDto.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.16
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatedEventResultDto {
	private String id;
	private boolean success;
	private String message;

	@Builder
	private CreatedEventResultDto(String id, boolean success, String message) {
		this.id = id;
		this.success = success;
		this.message = message;
	}

	public static CreatedEventResultDto from(EventResult eventResult) {
		return CreatedEventResultDto.builder()
			.id(eventResult.getId())
			.message(eventResult.getMessage())
			.success(eventResult.isSuccess())
			.build();
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean isFailure() {
		return !success;
	}
}
