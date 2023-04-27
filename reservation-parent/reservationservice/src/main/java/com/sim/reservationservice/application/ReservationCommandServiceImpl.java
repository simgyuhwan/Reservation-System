package com.sim.reservationservice.application;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.error.ErrorMessage;
import com.sim.reservationservice.dao.PerformanceInfoRepository;
import com.sim.reservationservice.dao.ReservationRepository;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.domain.PerformanceSchedule;
import com.sim.reservationservice.domain.Reservation;
import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.dto.response.ReservationInfoDto;
import com.sim.reservationservice.error.PerformanceInfoNotFoundException;
import com.sim.reservationservice.error.ReservationNotPossibleException;
import com.sim.reservationservice.error.SoldOutException;

import lombok.RequiredArgsConstructor;

/**
 * ReservationCommandServiceImpl.java
 * 예약 Command 구현 클래스
 *
 * @author sgh
 * @since 2023.04.26
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService {
	private final PerformanceInfoRepository performanceInfoRepository;
	private final ReservationRepository reservationRepository;

	@Override
	public ReservationInfoDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto) {
		PerformanceInfo performanceInfo = findPerformanceById(performanceId);
		PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);

		validationReservation(performanceInfo, schedule);

		Reservation reservation = reservationRepository.save(Reservation.of(reservationDto, schedule));

		return ReservationInfoDto.of(reservation, schedule, performanceInfo.getName());
	}

	private void validationReservation(PerformanceInfo performanceInfo, PerformanceSchedule schedule) {
		if (!performanceInfo.isAvailable()) {
			throw new ReservationNotPossibleException(ErrorMessage.RESERVATION_NOT_AVAILABLE,
				performanceInfo.getPerformanceId());
		}

		if (schedule.isSoldOut()) {
			throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, schedule.getId());
		}
	}

	private PerformanceSchedule findPerformanceSchedule(PerformanceInfo performanceInfo, Long scheduleId) {
		return performanceInfo.findScheduleById(scheduleId)
			.orElseThrow(() -> new NoSuchElementException(
				String.format("내부에 일치하는 performanceSchedule이 없습니다. performanceScheduleId : %s", scheduleId)));
	}

	private PerformanceInfo findPerformanceById(Long performanceId) {
		return performanceInfoRepository.findById(performanceId)
			.orElseThrow(() -> new PerformanceInfoNotFoundException(ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
	}

}

