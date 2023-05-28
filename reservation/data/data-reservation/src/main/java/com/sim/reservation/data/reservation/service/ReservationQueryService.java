package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.dto.ReservationInfo;

/**
 * 예약 Query 서비스
 */
public interface ReservationQueryService {

	/**
	 * 예약 정보 조회
	 */
	ReservationInfo findReservationInfoById(Long reservationId);
}
