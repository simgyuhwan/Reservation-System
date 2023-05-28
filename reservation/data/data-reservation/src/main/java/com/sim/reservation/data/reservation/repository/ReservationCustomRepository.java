package com.sim.reservation.data.reservation.repository;

import com.sim.reservation.data.reservation.dto.ReservationInfo;

/**
 * 예약 정보 QueryDsl Repository
 */
public interface ReservationCustomRepository {
	/**
	 * 예약 정보 조회
	 */
	ReservationInfo findReservationInfoById(Long reservationId);
}
