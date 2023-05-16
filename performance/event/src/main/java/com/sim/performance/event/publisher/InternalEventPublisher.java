package com.sim.performance.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.sim.performance.event.core.EventResult;
import com.sim.performance.event.dto.CreatedEventDto;
import com.sim.performance.event.dto.CreatedEventResultDto;
import com.sim.performance.event.payload.PerformanceCreatedPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceEventPublisher.java
 * 내부 이벤트 발행 클래스
 *
 * @author sgh
 * @since 2023.05.16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InternalEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 공연 생성 내부 이벤트 발행
     */
    public void publishPerformanceCreatedEvent(CreatedEventDto createdEventDto) {
        log.info("Publishing performance created event. performanceId : {}", createdEventDto.getPerformanceId());
        eventPublisher.publishEvent(PerformanceCreatedPayload.from(createdEventDto));
    }

    /**
     * 공연 생성 결과 내부 이벤트 발행
     */
    public void publishPerformanceCreatedEventResult(EventResult eventResult) {
        log.info("Publishing performance created event result. eventId : {}", eventResult.getId());
        eventPublisher.publishEvent(CreatedEventResultDto.from(eventResult));
    }
}
