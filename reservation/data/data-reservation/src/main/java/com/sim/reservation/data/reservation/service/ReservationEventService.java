package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.EventResult;
import com.sim.reservation.data.reservation.event.payload.PerformanceCreatedPayload;

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
