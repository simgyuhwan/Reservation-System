package com.sim.reservation.boot.service;

import com.sim.reservation.boot.dto.request.ReservationApplyRequest;
import com.sim.reservation.boot.dto.response.ReservationCancelResponse;
import com.sim.reservation.boot.dto.response.ReservationResultResponse;

/**
 * ReservationService.java
 * 예약 관련 서비스
 *
 * @author sgh
 * @since 2023.05.15
 */
public interface ReservationService {

    /**
     * 예약 신청
     *
     * @param performanceId 공연 ID
     * @param scheduleId 공연 시간 ID
     * @param reservationApplyRequest 예약 신청 정보
     *
     * @return 예약 신청 결과 메시지
     */
    ReservationResultResponse applyReservation(Long performanceId, Long scheduleId, ReservationApplyRequest reservationApplyRequest);

    /**
     * 예약 취소 신청
     *
     * @param reservationId 취소할 예약 ID
     * @return 예약 취소 메시지
     */
    ReservationCancelResponse cancelReservation(Long reservationId);
}
