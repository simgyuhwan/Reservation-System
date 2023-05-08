package com.reservation.performanceservice.event;

import com.reservation.common.event.payload.Payload;
import com.reservation.performanceservice.domain.Performance;

import lombok.Getter;

@Getter
public class PerformanceCreatedPayload implements Payload {
	private Long performanceId;
	private Long memberId;

	private PerformanceCreatedPayload(Long performanceId, Long memberId) {
		this.performanceId = performanceId;
		this.memberId = memberId;
	}

	public static PerformanceCreatedPayload from(Performance performance) {
		return new PerformanceCreatedPayload(performance.getId(), performance.getMemberId());
	}
}
