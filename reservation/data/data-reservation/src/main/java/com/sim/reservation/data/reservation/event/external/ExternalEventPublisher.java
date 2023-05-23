package com.sim.reservation.data.reservation.event.external;

import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.payload.Payload;

/**
 * 외부 이벤트 발행 클래스
 */
public interface ExternalEventPublisher {
	/**
	 * 공연 등록 이벤트 발행
	 */
	void publishReservationApplyEvent(DefaultEvent<Payload> defaultEvent);
}
