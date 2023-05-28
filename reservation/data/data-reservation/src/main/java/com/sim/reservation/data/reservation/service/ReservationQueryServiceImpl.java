package com.sim.reservation.data.reservation.service;

import org.springframework.stereotype.Service;

import com.sim.reservation.data.reservation.dto.ReservationInfo;
import com.sim.reservation.data.reservation.repository.ReservationCustomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationQueryServiceImpl implements ReservationQueryService{
	private final ReservationCustomRepository reservationCustomRepository;

	@Override
	public ReservationInfo findReservationInfoById(Long reservationId) {
		return reservationCustomRepository.findReservationInfoById(reservationId);
	}
}
