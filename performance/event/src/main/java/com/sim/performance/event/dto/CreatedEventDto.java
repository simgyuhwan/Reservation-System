package com.sim.performance.event.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceEventDto.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.16
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatedEventDto {
	private String id;
	private Long performanceId;
	private Long memberId;

	@Builder
	public CreatedEventDto(String id, Long performanceId, Long memberId) {
		this.id = id;
		this.performanceId = performanceId;
		this.memberId = memberId;
	}
}
