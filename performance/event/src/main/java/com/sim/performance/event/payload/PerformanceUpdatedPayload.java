package com.sim.performance.event.payload;

import com.sim.performance.event.dto.InternalEventDto;

import lombok.Builder;
import lombok.Getter;

/**
 * 공연 수정 이벤트 Payload
 */
@Getter
public class PerformanceUpdatedPayload implements Payload {
	private String id;
	private Long performanceId;
	private Long memberId;

	@Builder
	private PerformanceUpdatedPayload(String id, Long performanceId, Long memberId) {
		this.id = id;
		this.performanceId = performanceId;
		this.memberId = memberId;
	}

	public static PerformanceUpdatedPayload from(InternalEventDto internalEventDto) {
		return PerformanceUpdatedPayload.builder()
			.id(internalEventDto.getId())
			.performanceId(internalEventDto.getPerformanceId())
			.memberId(internalEventDto.getMemberId())
			.build();
	}
}
