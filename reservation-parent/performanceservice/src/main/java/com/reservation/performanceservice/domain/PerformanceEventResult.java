package com.reservation.performanceservice.domain;

import com.reservation.common.event.EventResult;
import com.reservation.common.model.BaseEntity;
import com.reservation.common.type.EventStatusTypes;

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
	private EventStatusTypes status;

	private String payload;

	private String message;

	@Builder
	private PerformanceEventResult(String id, EventStatusTypes status, String payload, String message) {
		this.id = id;
		this.status = status;
		this.payload = payload;
		this.message = message;
	}

	public void update(EventResult result) {
		if(result.isSuccess()) {
			this.status = EventStatusTypes.SUCCESS;
		}else {
			this.status = EventStatusTypes.FAIL;
			this.message = result.getMessage();
		}
	}

}
