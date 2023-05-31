package com.sim.reservation.data.reservation.event.external;

import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.payload.Payload;

/**
 * 외부 이벤트 발행 클래스
 */
public interface ExternalEventPublisher {

	/**
	 * 예약 신청 이벤트 발행
	 *
	 * @param defaultEvent 이벤트
	 */
	void publishReservationApplyEvent(DefaultEvent<Payload> defaultEvent);

	/**
	 * 예약 취소 신청 이벤트 발행
	 *
	 * @param defaultEvent 이벤트
	 */
	void publishReservationCancelEvent(DefaultEvent<Payload> defaultEvent);
}
