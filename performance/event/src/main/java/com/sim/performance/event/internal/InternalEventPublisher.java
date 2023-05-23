package com.sim.performance.event.internal;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.sim.performance.event.core.EventResult;
import com.sim.performance.event.internal.InternalEventDto;
import com.sim.performance.event.external.CreatedEventResultDto;
import com.sim.performance.event.external.UpdatedEventResultDto;
import com.sim.performance.event.core.payload.PerformanceCreatedPayload;
import com.sim.performance.event.core.payload.PerformanceUpdatedPayload;

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
    public void publishPerformanceCreatedEvent(InternalEventDto internalEventDto) {
        log.info("Creation of performances Publication of internal events. performanceId : {}", internalEventDto.getPerformanceId());
        eventPublisher.publishEvent(PerformanceCreatedPayload.from(internalEventDto));
    }

    /**
     * 공연 생성 결과, 내부 이벤트 발행
     */
    public void publishPerformanceCreatedEventResult(EventResult eventResult) {
        log.info("Creation of performances Publication of internal events result. eventId : {}", eventResult.getId());
        eventPublisher.publishEvent(CreatedEventResultDto.from(eventResult));
    }

    /**
     * 공연 수정 내부 이벤트 발행
     * @param internalEventDto
     */
    public void publishPerformanceUpdatedEvent(InternalEventDto internalEventDto) {
        log.info("Modification of performance Publication of internal events. performanceId : {}", internalEventDto.getPerformanceId());
        eventPublisher.publishEvent(PerformanceUpdatedPayload.from(internalEventDto));
    }

    /**
     * 공연 수정 결과, 내부 이벤트 발행
     * @param eventResult
     */
    public void publishPerformanceUpdatedEventResult(EventResult eventResult) {
        log.info("Modification of performances Publication of internal events result. eventId : {}", eventResult.getId());
        eventPublisher.publishEvent(UpdatedEventResultDto.from(eventResult));
    }
}
