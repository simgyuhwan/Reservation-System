package com.sim.reservation.boot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservation.boot.dto.request.ReservationApplyRequest;
import com.sim.reservation.boot.dto.response.ReservationResultResponse;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import com.sim.reservation.data.reservation.service.ReservationCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReservationServiceImpl.java
 * 예약 관련 서비스
 *
 * @author sgh
 * @since 2023.05.15
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    private final ReservationCommandService reservationCommandService;

    @Override
    public ReservationResultResponse applyReservation(Long performanceId, Long scheduleId,
        ReservationApplyRequest reservationApplyRequest) {
        ReservationDto reservationApplyDto = reservationApplyRequest.toReservationDto();
        ReservationDto reservationDto = reservationCommandService.createReservation(performanceId, scheduleId,
            reservationApplyDto);

        return ReservationResultResponse.applyComplete(reservationDto);
    }
}
