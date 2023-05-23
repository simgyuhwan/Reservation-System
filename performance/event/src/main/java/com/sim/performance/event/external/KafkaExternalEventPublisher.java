package com.sim.performance.event.external;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.sim.performance.event.core.PerformanceEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceProducer.java
 * Kafka를 활용한 이벤트 발행 클래스
 *
 * @author sgh
 * @since 2023.04.11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaExternalEventPublisher implements ExternalEventPublisher {
	private final StreamBridge streamBridge;

	/**
	 * 외부 서비스에게 공연 생성 이벤트 발행
	 *
	 * @param performanceEvent 공연 이벤트
	 */
	@Override
	public void publishPerformanceCreatedEvent(PerformanceEvent performanceEvent) {
		log.info("Publish events to external services : {}", performanceEvent);
		streamBridge.send("performance-service.performance.created", performanceEvent);
	}

	/**
	 * 외부 서비스에게 공연 수정 이벤트 발행
	 *
	 * @param performanceEvent 공연 이벤트
	 */
	@Override
	public void publishPerformanceUpdatedEvent(PerformanceEvent performanceEvent) {
		log.info("Publish events to external services : {}", performanceEvent);
		streamBridge.send("performance-service.performance.updated", performanceEvent);
	}
}
