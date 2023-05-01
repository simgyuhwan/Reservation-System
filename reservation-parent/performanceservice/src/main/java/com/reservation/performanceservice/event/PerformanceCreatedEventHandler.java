package com.reservation.performanceservice.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.reservation.performanceservice.application.PerformanceProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PerformanceCreatedEventHandler {
	private final PerformanceProducer performanceProducer;

	@Async("defaultExecutor")
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void sendCreatedEvent(PerformanceCreatedEvent performanceCreatedEvent) {
		log.info("Publishing a performance creation event");
		performanceProducer.sendPerformance(performanceCreatedEvent);
	}

	@Async("defaultExecutor")
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void sendNotification(PerformanceCreatedEvent performanceCreatedEvent) {
		log.info("Publishing performance creation event to notification service");
		// 알림 서비스로 전송
	}
}
