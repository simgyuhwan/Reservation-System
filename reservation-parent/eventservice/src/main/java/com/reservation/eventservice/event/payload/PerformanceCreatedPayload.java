package com.reservation.eventservice.event.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PerformanceCreatedPayload {
	private Long performanceId;
	private Long memberId;

	@Builder
	public PerformanceCreatedPayload(Long performanceId, Long memberId) {
		this.performanceId = performanceId;
		this.memberId = memberId;
	}
}
