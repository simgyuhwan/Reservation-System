package com.sim.reservationservice.application;

import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.dto.response.ReservationInfoDto;

/**
 * ReservationCommandService.java
 * 예약 Command 구현 클래스
 *
 * @author sgh
 * @since 2023.04.26
 */
public interface ReservationCommandService {
	ReservationInfoDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto);
}
