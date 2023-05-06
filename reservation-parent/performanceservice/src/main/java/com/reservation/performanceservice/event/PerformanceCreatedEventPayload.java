package com.reservation.performanceservice.event;

import com.reservation.common.event.payload.EventPayload;
import com.reservation.performanceservice.domain.Performance;

import lombok.Getter;

@Getter
public class PerformanceCreatedEventPayload implements EventPayload {
	private Long performanceId;
	private Long memberId;

	private PerformanceCreatedEventPayload(Long performanceId, Long memberId) {
		this.performanceId = performanceId;
		this.memberId = memberId;
	}

	public static PerformanceCreatedEventPayload from(Performance performance) {
		return new PerformanceCreatedEventPayload(performance.getId(), performance.getMemberId());
	}
}
