package com.sim.reservationservice.application;

import org.springframework.stereotype.Service;

import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.dto.response.ReservationInfoDto;

import lombok.RequiredArgsConstructor;

/**
 * ReservationCommandServiceImpl.java
 * 예약 Command 구현 클래스
 *
 * @author sgh
 * @since 2023.04.26
 */
@Service
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService {
	@Override
	public ReservationInfoDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto) {
		return null;
	}
}
