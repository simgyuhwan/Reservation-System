package com.sim.reservationservice.application;

import com.reservation.common.event.DefaultEvent;
import com.reservation.common.event.EventResult;
import com.reservation.common.event.payload.PerformanceCreatedPayload;

/**
 * EventService.java
 * 예약 이벤트 서비스
 *
 * @author sgh
 * @since 2023.05.11
 */
public interface ReservationEventService {
    EventResult savePerformanceInfo(DefaultEvent<PerformanceCreatedPayload> defaultEvent);
}
