package com.sim.performance.performancedomain.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sim.performance.performancedomain.service.PerformanceEventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventScheduler {
	private final PerformanceEventService performanceEventService;

	/**
	 * 공연 수정 Retry 이벤트 재발행
	 */
	@Scheduled(cron = "0 0/10 * * * *")
	public void rePublishUpdateEvent(){
		performanceEventService.rePublishPerformanceUpdateEvent();
	}
}
