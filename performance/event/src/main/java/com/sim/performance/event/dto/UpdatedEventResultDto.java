package com.sim.performance.event.dto;

import com.sim.performance.event.core.EventResult;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CreatedEventResultDto.java
 * 공연 정보 수정 이벤트 결과 DTO
 *
 * @author sgh
 * @since 2023.05.16
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatedEventResultDto {
	private String id;
	private boolean success;
	private String message;

	@Builder
	private UpdatedEventResultDto(String id, boolean success, String message) {
		this.id = id;
		this.success = success;
		this.message = message;
	}

	public static UpdatedEventResultDto from(EventResult eventResult) {
		return UpdatedEventResultDto.builder()
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
