package com.sim.reservation.data.reservation.event.external;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.payload.Payload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Kafka를 통한 외부 이벤트 발행
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaExternalEventPublisher implements ExternalEventPublisher{
	private final StreamBridge streamBridge;

	/**
	 * 예약 신청 이벤트 외부로 발행
	 */
	@Override
	public void publishReservationApplyEvent(DefaultEvent<Payload> defaultEvent) {
		streamBridge.send("reservation-service.reservation-apply",defaultEvent);
	}
}
