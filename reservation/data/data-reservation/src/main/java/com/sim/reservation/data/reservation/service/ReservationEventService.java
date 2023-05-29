package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.EventResult;
import com.sim.reservation.data.reservation.event.consumer.ReservationApplyCompleteEvent;
import com.sim.reservation.data.reservation.event.payload.PerformanceEventPayload;

/**
 * EventService.java
 * 예약 이벤트 서비스
 *
 * @author sgh
 * @since 2023.05.11
 */
public interface ReservationEventService {
    /**
     * 공연 예약 정보 저장
     *
     * @param defaultEvent 이벤트
     * @return 이벤트 결과
     */
    EventResult savePerformanceInfo(DefaultEvent<PerformanceEventPayload> event);

    /**
     * 공연 예약 정보 수정
     *
     * @param defaultEvent 이벤트
     * @return 이벤트 결과
     */
    EventResult updatePerformanceInfo(DefaultEvent<PerformanceEventPayload> event);

    /**
     * 예약 신청 완료 이벤트 저장
     */
    void saveEvent(ReservationApplyCompleteEvent reservationApplyCompleteEvent);
}
