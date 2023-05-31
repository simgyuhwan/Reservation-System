package com.sim.reservation.data.reservation.event.internal;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.sim.reservation.data.reservation.domain.EventStatus;
import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.external.ExternalEventPublisher;
import com.sim.reservation.data.reservation.event.payload.Payload;
import com.sim.reservation.data.reservation.event.payload.ReservationApplyEventPayload;
import com.sim.reservation.data.reservation.event.payload.ReservationCancelEventPayload;
import com.sim.reservation.data.reservation.repository.EventStatusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 내부 이벤트 헨들러
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InternalEventHandler {
	private final EventStatusRepository eventStatusRepository;
	private final ExternalEventPublisher externalEventPublisher;

	/**
	 *  예약 신청 이벤트 저장
	 */
	@EventListener
	public void saveEvent(ReservationApplyEventPayload reservationApplyEventPayload) {
		EventStatus eventStatus = EventStatus.createStartEvent(reservationApplyEventPayload.getId());
		eventStatusRepository.save(eventStatus);
	}

	/**
	 * 예약 신청 이벤트 외부로 발행
	 */
	@Async
	@TransactionalEventListener
	public void publishEventExternal(ReservationApplyEventPayload payload) {
		log.info("Publish outside the reservation apply event, eventId : {}", payload.getId());
		DefaultEvent<Payload> event = DefaultEvent.reservationApplyEvent(payload);
		externalEventPublisher.publishReservationApplyEvent(event);
	}

	/**
	 *  예약 취소 신청 이벤트 저장
	 */
	@EventListener
	public void saveEvent(ReservationCancelEventPayload reservationCancelEventPayload) {
		EventStatus eventStatus = EventStatus.createStartEvent(reservationCancelEventPayload.getId());
		eventStatusRepository.save(eventStatus);
	}

	/**
	 * 예약 취소 신청 이벤트 외부 발행
	 */
	@Async
	@TransactionalEventListener
	public void publishEventExternal(ReservationCancelEventPayload payload) {
		log.info("Publish outside the reservation cancel event, eventId : {}", payload.getId());
		DefaultEvent<Payload> event = DefaultEvent.reservationApplyEvent(payload);
		externalEventPublisher.publishReservationCancelEvent(event);
	}

}
