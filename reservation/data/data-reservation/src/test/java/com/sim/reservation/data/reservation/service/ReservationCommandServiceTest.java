package com.sim.reservation.data.reservation.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.domain.PerformanceSchedule;
import com.sim.reservation.data.reservation.domain.Reservation;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import com.sim.reservation.data.reservation.error.PerformanceInfoNotFoundException;
import com.sim.reservation.data.reservation.error.PerformanceScheduleNotFoundException;
import com.sim.reservation.data.reservation.error.ReservationNotPossibleException;
import com.sim.reservation.data.reservation.error.SoldOutException;
import com.sim.reservation.data.reservation.factory.PerformanceInfoFactory;
import com.sim.reservation.data.reservation.factory.PerformanceScheduleFactory;
import com.sim.reservation.data.reservation.factory.ReservationDtoFactory;
import com.sim.reservation.data.reservation.factory.ReservationFactory;
import com.sim.reservation.data.reservation.repository.PerformanceInfoRepository;
import com.sim.reservation.data.reservation.repository.PerformanceScheduleRepository;
import com.sim.reservation.data.reservation.repository.ReservationRepository;

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
	private static final int WAIT_TIME = 1;
	private static final int LEASE_TIME = 2;
	private static final long RESERVATION_ID = 1L;

	@InjectMocks
	private ReservationCommandServiceImpl reservationCommandService;

	@Mock
	private PerformanceInfoRepository performanceInfoRepository;

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private PerformanceScheduleRepository performanceScheduleRepository;

	@Mock
	private RedissonClient redissonClient;

	@Mock
	private RLock rLock;

	@BeforeEach
	void setUp(TestInfo info) throws InterruptedException {
		if(!info.getTags().contains("noLock")) {
			when(redissonClient.getLock(any())).thenReturn(rLock);
			when(rLock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS)).thenReturn(true);
		}
	}

	@Test
	@DisplayName("예약 신청 성공 : 예약 도메인 저장 확인")
	void reservationWholesalerSaveConfirmation() {
		//given
		PerformanceSchedule performanceSchedule = createPerformanceSchedule();
		PerformanceInfo performanceInfo = createPerformanceInfoWithSchedule(performanceSchedule);
		Reservation reservation = createReservationWithId(RESERVATION_ID);

		when(performanceScheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.of(performanceSchedule));
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(Optional.of(performanceInfo));
		when(reservationRepository.save(any())).thenReturn(reservation);

		//when
		reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
			createReservationDto());

		//then
		verify(reservationRepository).save(any());
	}

	@Test
	@DisplayName("예약 신청 성공 : 이용자 정보 일치 확인")
	void reservationRequestSuccessfulRegistrationValueConfirmation(){
		//given
		ReservationDto reservationDto = createReservationDto();
		PerformanceInfo performanceInfo = createPerformanceInfoWithSchedule(createPerformanceSchedule());
		Reservation reservation = createReservationWithId(RESERVATION_ID);

		when(performanceScheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.of(createPerformanceSchedule()));
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(Optional.of(performanceInfo));
		when(reservationRepository.save(any())).thenReturn(reservation);

		//when
		ReservationDto reservationInfoDto = reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
			reservationDto);

		//then
		assertThat(reservationInfoDto.getName()).isEqualTo(reservationDto.getName());
		assertThat(reservationInfoDto.getPhoneNum()).isEqualTo(reservationDto.getPhoneNum());
		assertThat(reservationInfoDto.getEmail()).isEqualTo(reservationDto.getEmail());
	}

	@Test
	@DisplayName("예약 신청 성공 : 하나 남은 좌석 예약 신청 후 매진 확인")
	void verificationOfSoldOutAfterReservation(){
		//given
		ReservationDto reservationDto = createReservationDto();
		PerformanceInfo infoWithOneSeats = createPerformanceInfoWithOneSeats();
		Reservation reservation = createReservationWithId(RESERVATION_ID);

		when(performanceScheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.of(createPerformanceSchedule()));
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(Optional.of(infoWithOneSeats));
		when(reservationRepository.save(any())).thenReturn(reservation);

		//when
		reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID,
			reservationDto);

		//then
		PerformanceSchedule schedule = infoWithOneSeats.getPerformanceSchedules().get(0);
		assertThat(schedule.isAvailable()).isFalse();
		assertThat(schedule.getRemainingSeats()).isEqualTo(0);
	}

	@Test
	@DisplayName("예약 신청 실패 : 등록된 공연 정보 없음 예외")
	void noRegisteredPerformanceInformationException() {
		when(performanceScheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.of(createPerformanceSchedule()));
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() ->
			reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID, createReservationDto())
		).isInstanceOf(PerformanceInfoNotFoundException.class);
	}

	@Test
	@DisplayName("예약 신청 실패 : 예약 불가능한 공연 예외")
	void unReservablePerformanceException() {
		PerformanceSchedule performanceSchedule = createPerformanceSchedule();
		PerformanceInfo performanceInfo = createDisablePerformanceInfo(List.of(performanceSchedule));
		when(performanceScheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.of(performanceSchedule));
		when(performanceInfoRepository.findById(PERFORMANCE_ID)).thenReturn(
			Optional.of(performanceInfo));

		assertThatThrownBy(() ->
			reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID, createReservationDto())
		).isInstanceOf(ReservationNotPossibleException.class);
	}

	@Test
	@Tag("noLock")
	@DisplayName("예약 신청 실패 : 매진된 공연 예외")
	void exceptionForPerformanceSoldOut() {
		when(performanceScheduleRepository.findById(SCHEDULE_ID)).thenReturn(Optional.of(createSoldOutPerformanceSchedule()));

		assertThatThrownBy(
			() -> reservationCommandService.createReservation(PERFORMANCE_ID, SCHEDULE_ID, createReservationDto()))
			.isInstanceOf(SoldOutException.class);
	}

	@Test
	@Tag("noLock")
	@DisplayName("예약 신청 실패 : 공연에 속하지 않은 공연 일자 예외")
	void excludingPerformanceDatesNotInTheSchedule() {
		assertThatThrownBy(
			() -> reservationCommandService.createReservation(PERFORMANCE_ID, -1L, createReservationDto()))
			.isInstanceOf(PerformanceScheduleNotFoundException.class);
	}

	private Reservation createReservationWithId(Long id) {
		return ReservationFactory.createReservationWithId(id);
	}

	private ReservationDto createReservationDto() {
		return ReservationDtoFactory.createReservationDto();
	}

	private PerformanceInfo createDisablePerformanceInfo(List<PerformanceSchedule> performanceSchedules) {
		return PerformanceInfoFactory.createDisablePerformanceInfo(performanceSchedules);
	}

	public PerformanceInfo createPerformanceInfoWithSchedule(PerformanceSchedule performanceSchedule) {
		return PerformanceInfoFactory.createPerformanceInfo(performanceSchedule);
	}

	public PerformanceInfo createPerformanceInfoWithOneSeats() {
		return PerformanceInfoFactory.createPerformanceInfoWithOneSeats();
	}

	public PerformanceSchedule createPerformanceSchedule() {
		return PerformanceScheduleFactory.createPerformanceSchedule(null);
	}

	public PerformanceSchedule createDisablePerformanceSchedule() {
		return PerformanceScheduleFactory.createDisablePerformanceSchedule(null);
	}

	public PerformanceSchedule createSoldOutPerformanceSchedule() {
		return PerformanceScheduleFactory.createSoldOutPerformanceSchedule(null);
	}

}