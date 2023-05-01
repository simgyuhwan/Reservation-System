package com.reservation.performanceservice.application;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.event.PerformanceCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceProducer.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.04.11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceProducer {
	private final StreamBridge streamBridge;

	public void sendPerformance(PerformanceCreatedEvent performanceCreatedEvent){
		log.info("Performance send : {}", performanceCreatedEvent);
		streamBridge.send("performance-info", performanceCreatedEvent);
	}
}
