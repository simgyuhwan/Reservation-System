package com.sim.reservation.boot.service;

import com.sim.reservation.boot.dto.request.ReservationApplyRequest;
import com.sim.reservation.boot.dto.response.ReservationResultResponse;

/**
 * ReservationService.java
 * 예약 관련 서비스
 *
 * @author sgh
 * @since 2023.05.15
 */
public interface ReservationService {
    ReservationResultResponse performanceReservation(Long performanceId, Long scheduleId, ReservationApplyRequest reservationApplyRequest);
}
