package com.sim.performance.event.payload;

import com.sim.performance.event.dto.CreatedEventDto;

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

	public static PerformanceCreatedPayload from(CreatedEventDto createdEventDto) {
		return PerformanceCreatedPayload.builder()
			.id(createdEventDto.getId())
			.performanceId(createdEventDto.getPerformanceId())
			.memberId(createdEventDto.getMemberId())
			.build();
	}
}
