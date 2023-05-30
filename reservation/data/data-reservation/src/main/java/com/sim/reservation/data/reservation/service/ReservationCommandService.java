package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.dto.ReservationDto;

/**
 * ReservationCommandService.java
 * 예약 Command Service
 *
 * @author sgh
 * @since 2023.05.15
 */
public interface ReservationCommandService {
    /**
     * 예약 도메인 생성
     *
     * @param performanceId 공연 ID
     * @param scheduleId 공연 시간 ID
     * @param reservationDto 예약 정보
     *
     * @return 저장된 공연 정보
     */
    ReservationDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto);

    /**
     * 예약 신청 삭제
     *
     * @param reservationId 예약 ID
     */
    void deleteReservation(Long reservationId);
}
