package com.reservation.common.event;

import java.time.LocalDateTime;
import java.util.UUID;

import com.reservation.common.type.EventStatusTypes;
import com.reservation.common.type.SourceType;

import lombok.Getter;

@Getter
public abstract class Event {
	private String id;
	private LocalDateTime eventDateTime;
	private String source;
	private String status;

	public Event(SourceType source, EventStatusTypes status) {
		this.id = UUID.randomUUID().toString();
		this.source = source.getName();
		this.eventDateTime = LocalDateTime.now();
		this.status = status.name();
	}
}
