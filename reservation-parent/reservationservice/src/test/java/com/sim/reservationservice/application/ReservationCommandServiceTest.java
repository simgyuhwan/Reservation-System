package com.sim.reservationservice.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sim.reservationservice.dao.PerformanceInfoRepository;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.error.PerformanceInfoNotFoundException;
import com.sim.reservationservice.error.ReservationNotPossibleException;
import com.sim.reservationservice.factory.ReservationCommandDataFactory;
import com.sim.reservationservice.factory.ReservationQueryDataFactory;

/**
 * ReservationCommandServiceTest.java
 * 예약 Command Service 테스트
 *
 * @author sgh
 * @since 2023.04.26
 */
@ExtendWith(MockitoExtension.class)
class ReservationCommandServiceTest {
	private static final long PERFORMANCE_ID = 1L;
	private static final long SCHEDULE_ID = 1L;

	@InjectMocks
	private ReservationCommandServiceImpl reservationCommandService;

	@Mock
	private PerformanceInfoRepository performanceInfoRepository;

	@Test
	@DisplayName("예약 신청 : 등록된 공연 정보 없음 예외")
	void noRegisteredPerformanceInformationException() {
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(Optional.ofNullable(null));

		assertThatThrownBy(() ->
			reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID, createReservationDto())
		).isInstanceOf(PerformanceInfoNotFoundException.class);
	}

	@Test
	@DisplayName("예약 신청 : 예약 불가능한 공연 예외")
	void unReservablePerformanceException() {
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(
			Optional.of(createDisablePerformanceInfo()));

		assertThatThrownBy(() ->
			reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID, createReservationDto())
		).isInstanceOf(ReservationNotPossibleException.class);
	}

	@Test
	@DisplayName("예약 신청 : 매진된 공연 예외")
	void exceptionForPerformanceSoldOut() {
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(
			Optional.of(createSoldOutPerformanceInfo()));

		assertThatThrownBy(
			() -> reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID, createReservationDto()));
	}

	@Test
	@DisplayName("예약 신청 : 공연에 속하지 않은 공연 일자 예외")
	void excludingPerformanceDatesNotInTheSchedule() {
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(
			Optional.of(createSoldOutPerformanceInfo()));

		assertThatThrownBy(
			() -> reservationCommandService.createReservation(PERFORMANCE_ID, -1L, createReservationDto()));
	}

	private ReservationDto createReservationDto() {
		return ReservationCommandDataFactory.createReservationDto();
	}

	private PerformanceInfo createDisablePerformanceInfo() {
		return ReservationQueryDataFactory.createDisablePerformanceInfo();
	}

	private PerformanceInfo createSoldOutPerformanceInfo() {
		return ReservationQueryDataFactory.createSoldOutPerformanceInfo();
	}
}