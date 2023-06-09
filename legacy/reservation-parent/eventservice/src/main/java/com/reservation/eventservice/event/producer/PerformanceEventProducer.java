package com.reservation.eventservice.event.producer;

import com.reservation.common.event.DefaultEvent;

/**
 * 예약 서비스로 발행 클래스
 */
public interface PerformanceEventProducer {
	void publishCreatedEvent(DefaultEvent createdEvent);
}
