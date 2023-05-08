package com.reservation.performanceservice.event.factory.creator;

import com.reservation.common.event.payload.EventPayload;
import com.reservation.performanceservice.domain.Performance;

/**
 * PayloadCreator.java
 * Payload 생성 인터페이스
 *
 * @author sgh
 * @since 2023.05.08
 */
@FunctionalInterface
public interface PayloadCreator {
    EventPayload createPayload(Performance performance);
}
