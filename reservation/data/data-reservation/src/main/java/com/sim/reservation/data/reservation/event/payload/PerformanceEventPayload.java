package com.sim.reservation.data.reservation.event.payload;

import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PerformanceEventPayload implements Payload{
	private String id;
	private Long performanceId;
	private Long memberId;

	@Builder
	public PerformanceEventPayload(String id, Long performanceId, Long memberId) {
		this.id = id;
		this.performanceId = performanceId;
		this.memberId = memberId;
	}

	public Long getPerformanceId() {
		Assert.notNull(performanceId, "performanceId is must be not null");
		return performanceId;
	}

}
