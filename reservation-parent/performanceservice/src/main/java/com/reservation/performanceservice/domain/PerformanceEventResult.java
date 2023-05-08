package com.reservation.performanceservice.domain;

import com.reservation.common.event.EventResult;
import com.reservation.common.model.BaseEntity;
import com.reservation.common.types.EventStatusType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PerformanceEventResult extends BaseEntity {
	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private EventStatusType status;

	private String payload;

	private String message;

	@Builder
	private PerformanceEventResult(String id, EventStatusType status, String payload, String message) {
		this.id = id;
		this.status = status;
		this.payload = payload;
		this.message = message;
	}

	public void update(EventResult result) {
		if(result.isSuccess()) {
			this.status = EventStatusType.SUCCESS;
		}else {
			this.status = EventStatusType.FAIL;
			this.message = result.getMessage();
		}
	}

}
