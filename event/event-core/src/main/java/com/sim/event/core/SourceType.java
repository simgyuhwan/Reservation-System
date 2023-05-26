package com.sim.event.core;

import lombok.Getter;

/**
 * 이벤트 송신 출처
 */
@Getter
public enum SourceType {
	MEMBER_SERVICE,
	RESERVATION_SERVICE,
	PERFORMANCE_SERVICE,
	NOTIFICATION_SERVICE,
	EVENT_SERVICE;
}
