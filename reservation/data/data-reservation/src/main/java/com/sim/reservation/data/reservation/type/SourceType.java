package com.sim.reservation.data.reservation.type;

import lombok.Getter;

@Getter
public enum SourceType {
	MEMBER_SERVICE,
	RESERVATION_SERVICE,
	PERFORMANCE_SERVICE,
	NOTIFICATION_SERVICE,
	EVENT_SERVICE;
}
