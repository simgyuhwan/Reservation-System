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
    void createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto);
}
