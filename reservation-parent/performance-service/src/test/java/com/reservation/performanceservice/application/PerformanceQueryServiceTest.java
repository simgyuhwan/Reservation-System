package com.reservation.performanceservice.application;

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

import com.reservation.common.error.ErrorCode;
import com.reservation.performanceservice.application.mapper.PerformanceDayMapper;
import com.reservation.performanceservice.application.mapper.PerformanceRegisterMapper;
import com.reservation.performanceservice.dao.PerformanceDayRepository;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceRegisterDto;
import com.reservation.performanceservice.error.InvalidPerformanceDateException;
import com.reservation.performanceservice.factory.PerformanceTestDataFactory;

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
		when(performanceRepository.save(any())).thenReturn(createPerformance());
		PerformanceRegisterDto registerDto = createPerformanceRegisterDto("2023-05-01", "2023-04-01");

		//when, then
		assertThatThrownBy(() -> performanceQueryService.createPerformance(registerDto))
			.isInstanceOf(InvalidPerformanceDateException.class)
			.hasMessage(ErrorCode.PERFORMANCE_END_DATE_BEFORE_START_DATE.getMessage());
	}

	@Test
	@DisplayName("공연 등록 : 공연 시작일이 지난 날짜이면 예외 발생")
	void wrongPerformanceStartDateException() {
		//given
		when(performanceRepository.save(any())).thenReturn(createPerformance());
		PerformanceRegisterDto registerDto = createPerformanceRegisterDto("1990-01-01", "2023-04-01");

		//when, then
		assertThatThrownBy(() -> performanceQueryService.createPerformance(registerDto))
			.isInstanceOf(InvalidPerformanceDateException.class)
			.hasMessage(ErrorCode.PERFORMANCE_START_DATE_IN_THE_PAST.getMessage());
	}

	@Test
	@DisplayName("공연 등록 성공")
	void performanceRegisterSuccessTest() {
		//given
		when(performanceRepository.save(any())).thenReturn(createPerformance());

		//when
		performanceQueryService.createPerformance(createPerformanceRegisterDto());

		//then
		then(performanceRepository).should().save(any(Performance.class));
		then(performanceDayRepository).should().saveAll(any());
	}

	private PerformanceRegisterDto createPerformanceRegisterDto() {
		return PerformanceTestDataFactory.createPerformanceRegisterDto();
	}

	private Performance createPerformance() {
		return PerformanceTestDataFactory.createPerformance();
	}

	private PerformanceRegisterDto createPerformanceRegisterDto(String start, String end) {
		return PerformanceTestDataFactory.createPerformanceRegisterDto(start, end);
	}
}