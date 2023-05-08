package com.reservation.performanceservice.event.producer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.reservation.common.event.payload.Payload;
import com.reservation.performanceservice.event.PerformanceEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceProducer.java
 * Kafka를 활용한 메시징 발행 클래스
 *
 * @author sgh
 * @since 2023.04.11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPerformanceProducer implements PerformanceProducer{
	private final StreamBridge streamBridge;

	@Override
	public void publishCreatedEvent(PerformanceEvent performanceEvent) {
		log.info("Publishing Performance Created Event : {}", performanceEvent);
		streamBridge.send("performance-service.performance.created", performanceEvent);
	}

}
