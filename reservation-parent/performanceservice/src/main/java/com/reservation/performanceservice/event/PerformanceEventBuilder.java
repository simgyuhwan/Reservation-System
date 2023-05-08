package com.reservation.performanceservice.event;

import com.reservation.common.event.payload.EventPayload;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.event.factory.creator.PayloadCreator;
import com.reservation.performanceservice.types.EventType;

/**
 * PerformanceEventBuilder.java
 * 공연 생성 이벤트 빌더
 *
 * @author sgh
 * @since 2023.05.08
 */
public class PerformanceEventBuilder<T extends PerformanceEvent<EventPayload>> {

    public static<T> Builder<T> withEventType(EventType eventType, Performance performance) {
        return new Builder<>(eventType, performance);
    }

    public static class Builder<T> {
        private EventType eventType;
        private Performance performance;
        private PayloadCreator payloadCreator;

        public Builder(EventType eventType, Performance performance) {
            this.eventType = eventType;
            this.performance = performance;
        }

        public Builder<T> withPayload(PayloadCreator payloadCreator) {
            this.payloadCreator = payloadCreator;
            return this;
        }

        public PerformanceEvent<EventPayload> create() {
            EventPayload payload = payloadCreator.createPayload(performance);
            return PerformanceEvent.from(eventType, payload);
        }
    }
}
