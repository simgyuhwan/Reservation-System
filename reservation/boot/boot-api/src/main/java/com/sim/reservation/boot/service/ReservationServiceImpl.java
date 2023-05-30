package com.sim.reservation.boot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservation.boot.dto.request.ReservationApplyRequest;
import com.sim.reservation.boot.dto.response.ReservationCancelResponse;
import com.sim.reservation.boot.dto.response.ReservationResultResponse;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import com.sim.reservation.data.reservation.dto.ReservationInfo;
import com.sim.reservation.data.reservation.service.ReservationCommandService;
import com.sim.reservation.data.reservation.service.ReservationQueryService;

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
    private final ReservationQueryService reservationQueryService;

    /**
     * 예약 신청
     *
     * @param performanceId 공연 ID
     * @param scheduleId 공연 시간 ID
     * @param reservationApplyRequest 예약 신청 정보
     *
     * @return 예약 신청 결과 메시지
     */
    @Override
    public ReservationResultResponse applyReservation(Long performanceId, Long scheduleId,
        ReservationApplyRequest reservationApplyRequest) {
        ReservationDto reservationApplyDto = reservationApplyRequest.toReservationDto();
        ReservationDto reservationDto = reservationCommandService.createReservation(performanceId, scheduleId,
            reservationApplyDto);

        return ReservationResultResponse.applyComplete(reservationDto);
    }

    /**
     * 예약 취소 신청
     *
     * @param reservationId 취소할 예약 ID
     * @return 예약 취소 메시지
     */
    @Override
    public ReservationCancelResponse cancelReservation(Long reservationId) {
        ReservationInfo reservationInfo = reservationQueryService.findReservationInfoById(reservationId);
        return null;
    }
}
