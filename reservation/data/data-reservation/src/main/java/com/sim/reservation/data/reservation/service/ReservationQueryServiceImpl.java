package com.sim.reservation.data.reservation.service;

import com.sim.reservation.data.reservation.dto.ReservationInfo;
import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.ReservationInfoNotFoundException;
import com.sim.reservation.data.reservation.repository.ReservationCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 예약 Query 서비스
 */
@Service
@RequiredArgsConstructor
public class ReservationQueryServiceImpl implements ReservationQueryService{
	private final ReservationCustomRepository reservationCustomRepository;

	/**
	 * 예약 정보 조회
	 *
	 * @param reservationId 예약 ID
	 * @return 예약 정보
	 */
	@Override
	public ReservationInfo findReservationInfoById(Long reservationId) {
		return reservationCustomRepository.findReservationInfoById(reservationId)
			.orElseThrow(() -> new ReservationInfoNotFoundException(ErrorMessage.RESERVATION_NOT_FOUND, reservationId));
	}
}
