package com.sim.performance.event.internal;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceEventDto.java
 * 내부 이벤트 전달용 DTO
 *
 * @author sgh
 * @since 2023.05.16
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InternalEventDto {
	private String id;
	private Long performanceId;
	private Long memberId;

	@Builder
	public InternalEventDto(String id, Long performanceId, Long memberId) {
		this.id = id;
		this.performanceId = performanceId;
		this.memberId = memberId;
	}
}
