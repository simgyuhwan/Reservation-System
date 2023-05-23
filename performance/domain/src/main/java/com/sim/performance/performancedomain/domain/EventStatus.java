package com.sim.performance.performancedomain.domain;

import com.sim.performance.event.core.payload.Payload;
import com.sim.performance.event.core.type.EventStatusType;
import com.sim.performance.event.core.type.EventType;

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
	private Long performanceId;

	@Enumerated(EnumType.STRING)
	private EventStatusType status;

	@Enumerated(EnumType.STRING)
	private EventType eventType;

	private String message;

	@Builder
	private EventStatus(String id, Long performanceId, EventStatusType status, EventType eventType) {
		this.id = id;
		this.performanceId = performanceId;
		this.status = status;
		this.eventType = eventType;
	}

	public static EventStatus from(Payload payload, EventType eventType) {
		return EventStatus.builder()
			.id(payload.getId())
			.performanceId(payload.getPerformanceId())
			.status(EventStatusType.PENDING)
			.eventType(eventType)
			.build();
	}

	public boolean isCompleted() {
		return EventStatusType.SUCCESS.equals(status);
	}

	public void changeToCompleted() {
		status = EventStatusType.SUCCESS;
	}

	public void changeToFailed(String message) {
		status = EventStatusType.FAIL;
		this.message = message;
	}

	public void changeToRetry(String message) {
		status = EventStatusType.RETRY;
		this.message = message;
	}
}
