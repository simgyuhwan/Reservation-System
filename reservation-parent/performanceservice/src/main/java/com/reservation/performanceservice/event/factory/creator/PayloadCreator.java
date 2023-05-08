package com.reservation.performanceservice.event.factory.creator;

import com.reservation.common.event.payload.Payload;
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
    Payload createPayload(Performance performance);
}
