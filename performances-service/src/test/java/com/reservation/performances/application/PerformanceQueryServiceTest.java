package com.reservation.performances.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservation.performances.application.mapper.PerformanceDayMapper;
import com.reservation.performances.application.mapper.PerformanceRegisterMapper;
import com.reservation.performances.dao.PerformanceDayRepository;
import com.reservation.performances.dao.PerformanceRepository;
import com.reservation.performances.domain.Performance;
import com.reservation.performances.dto.request.PerformanceRegisterDto;
import com.reservation.performances.error.ErrorCode;
import com.reservation.performances.error.InvalidPerformanceDateException;
import com.reservation.performances.global.factory.PerformanceTestDataFactory;

/**
 * PerformanceQueryServiceTest.java
 *
 * @author sgh
 * @since 2023.04.03
 */
@ExtendWith(MockitoExtension.class)
class PerformanceQueryServiceTest {

	@Mock
	private PerformanceDayRepository performanceDayRepository;

	@Mock
	private PerformanceRepository performanceRepository;

	@Spy
	private PerformanceDayMapper performanceDayMapper = PerformanceDayMapper.INSTANCE;

	@Spy
	private PerformanceRegisterMapper performanceRegisterMapper = PerformanceRegisterMapper.INSTANCE;

	@InjectMocks
	private PerformanceQueryServiceImpl performanceQueryService;

	@Test
	@DisplayName("공연 등록 : 공연 종료일이 시작일보다 앞에 있으면 예외 발생")
	void wrongPerformanceScheduleException() {
		//given
		when(performanceRepository.save(any())).thenReturn(PerformanceTestDataFactory.createPerformance());
		PerformanceRegisterDto registerDto = PerformanceTestDataFactory.createPerformanceRegisterDto(
			"2023-05-01", "2023-04-01");

		//when, then
		assertThatThrownBy(() -> performanceQueryService.createPerformance(registerDto))
			.isInstanceOf(InvalidPerformanceDateException.class)
			.hasMessage(ErrorCode.PERFORMANCE_END_DATE_BEFORE_START_DATE.getMessage());
	}

	@Test
	@DisplayName("공연 등록 : 공연 시작일이 지난 날짜이면 예외 발생")
	void wrongPerformanceStartDateException() {
		//given
		when(performanceRepository.save(any())).thenReturn(PerformanceTestDataFactory.createPerformance());
		PerformanceRegisterDto registerDto = PerformanceTestDataFactory.createPerformanceRegisterDto(
			"1990-01-01", "2023-04-01");

		//when, then
		assertThatThrownBy(() -> performanceQueryService.createPerformance(registerDto))
			.isInstanceOf(InvalidPerformanceDateException.class)
			.hasMessage(ErrorCode.PERFORMANCE_START_DATE_IN_THE_PAST.getMessage());
	}

	@Test
	@DisplayName("공연 등록 성공")
	void performanceRegisterSuccessTest() {
		//given
		when(performanceRepository.save(any())).thenReturn(PerformanceTestDataFactory.createPerformance());

		//when
		performanceQueryService.createPerformance(PerformanceTestDataFactory.createPerformanceRegisterDto());

		//then
		then(performanceRepository).should().save(any(Performance.class));
		then(performanceDayRepository).should().saveAll(any());
	}
}