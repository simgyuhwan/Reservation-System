package com.sim.reservation.data.reservation.event.internal;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.sim.reservation.data.reservation.event.payload.ReservationApplyEventPayload;
import com.sim.reservation.data.reservation.event.payload.ReservationCancelEventPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 내부 이벤트 발행 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InternalEventPublisher{
	private final ApplicationEventPublisher eventPublisher;

	/**
	 * 예약 신청 내부 이벤트 발행
	 * @param reservationApplyEventPayload
	 */
	public void publishReservationApplyEvent(ReservationApplyEventPayload reservationApplyEventPayload) {
		log.info("Publish reservation apply event, event id : {}", reservationApplyEventPayload.getId());
		eventPublisher.publishEvent(reservationApplyEventPayload);
	}

	public void publishReservationCancelEvent(ReservationCancelEventPayload reservationCancelEventPayload) {
		log.info("Publish reservation cancel event, event id : {}", reservationCancelEventPayload.getId());
		eventPublisher.publishEvent(reservationCancelEventPayload);
	}
}
