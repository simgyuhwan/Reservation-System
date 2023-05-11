package com.reservation.common.event;

import com.reservation.common.types.EventStatusType;

/**
 * EventResult.java
 * 이벤트 결과 값 인터페이스
 *
 * @author sgh
 * @since 2023.05.11
 */
public interface EventStatus {
	EventStatusType getStatus();
}
