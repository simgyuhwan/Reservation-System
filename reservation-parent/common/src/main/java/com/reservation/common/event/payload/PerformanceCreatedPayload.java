package com.reservation.common.event.payload;

import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PerformanceCreatedPayload implements Payload{
	private Long performanceId;
	private Long memberId;

	@Builder
	public PerformanceCreatedPayload(Long performanceId, Long memberId) {
		this.performanceId = performanceId;
		this.memberId = memberId;
	}

	public Long getPerformanceId() {
		Assert.notNull(performanceId, "performanceId is must be not null");
		return performanceId;
	}


}
