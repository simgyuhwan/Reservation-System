package com.sim.performance.event.core.payload;

import com.sim.performance.event.internal.InternalEventDto;

import lombok.Builder;
import lombok.Getter;

/**
 * 공연 생성 이벤트 Payload
 */
@Getter
public class PerformanceCreatedPayload implements Payload {
	private String id;
	private Long performanceId;
	private Long memberId;

	@Builder
	private PerformanceCreatedPayload(String id, Long performanceId, Long memberId) {
		this.id = id;
		this.performanceId = performanceId;
		this.memberId = memberId;
	}

	public static PerformanceCreatedPayload from(InternalEventDto internalEventDto) {
		return PerformanceCreatedPayload.builder()
			.id(internalEventDto.getId())
			.performanceId(internalEventDto.getPerformanceId())
			.memberId(internalEventDto.getMemberId())
			.build();
	}
}
