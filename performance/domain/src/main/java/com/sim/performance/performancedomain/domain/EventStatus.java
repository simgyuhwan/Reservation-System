package com.sim.performance.performancedomain.domain;

import com.sim.performance.event.core.PerformanceEvent;
import com.sim.performance.event.payload.PerformanceCreatedPayload;
import com.sim.performance.event.type.EventStatusType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * EventStatus.java
 * 이벤트 상태
 *
 * @author sgh
 * @since 2023.05.11
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventStatus extends BaseEntity {

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private EventStatusType status;

	private Long performanceId;

	@Builder
	private EventStatus(String id, EventStatusType status, Long performanceId) {
		this.id = id;
		this.status = status;
		this.performanceId = performanceId;
	}

	public static EventStatus from(PerformanceEvent performanceEvent, EventStatusType statusType) {
		PerformanceCreatedPayload payload = (PerformanceCreatedPayload)performanceEvent.getPayload();
		return EventStatus.builder()
			.id(performanceEvent.getId())
			.status(statusType)
			.performanceId(payload.getPerformanceId())
			.build();
	}

	public static EventStatus from(PerformanceCreatedPayload performanceCreatedPayload) {
		return EventStatus.builder()
			.id(performanceCreatedPayload.getId())
			.performanceId(performanceCreatedPayload.getPerformanceId())
			.status(EventStatusType.PENDING)
			.build();
	}

	public boolean isCompleted() {
		return EventStatusType.SUCCESS.equals(status);
	}

	public void changeToCompleted() {
		status = EventStatusType.SUCCESS;
	}

	public void changeToFailed() {
		status = EventStatusType.FAIL;
	}
}
