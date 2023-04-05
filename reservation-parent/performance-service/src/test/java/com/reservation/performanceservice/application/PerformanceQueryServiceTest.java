package com.reservation.performanceservice.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservation.common.error.ErrorCode;
import com.reservation.performanceservice.application.mapper.PerformanceDayMapper;
import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceDayRepository;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.InvalidPerformanceDateException;
import com.reservation.performanceservice.error.PerformanceNotFoundException;
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
	private PerformanceDtoMapper performanceDtoMapper = PerformanceDtoMapper.INSTANCE;

	@InjectMocks
	private PerformanceQueryServiceImpl performanceQueryService;

	@Test
	@DisplayName("공연 등록 실패: 공연 종료일이 공연 시작일보다 앞에 있으면 예외 발생")
	void wrongPerformanceScheduleException() {
		//given
		PerformanceDto registerDto = createPerformanceDto("2023-05-01", "2023-04-01");

		//when, then
		assertThatThrownBy(() -> performanceQueryService.createPerformance(registerDto))
			.isInstanceOf(InvalidPerformanceDateException.class)
			.hasMessage(ErrorCode.PERFORMANCE_END_DATE_BEFORE_START_DATE.getMessage());
	}

	@Test
	@DisplayName("공연 등록 실패: 공연 시작 일이 현재일 보다 이전 날짜이면 예외 발생")
	void wrongPerformanceStartDateException() {
		//given
		PerformanceDto registerDto = createPerformanceDto("1990-01-01", "2023-04-01");

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
		performanceQueryService.createPerformance(createPerformanceDto());

		//then
		then(performanceRepository).should().save(any(Performance.class));
	}

	@Test
	@DisplayName("공연 수정 실패: 등록된 공연 정보 없음, 예외 발생")
	void noRegisteredPerformanceInformationException() {
		//given
		Long performanceId = 1L;
		when(performanceRepository.findById(performanceId)).thenReturn(Optional.ofNullable(null));

		//then
		assertThatThrownBy(() -> performanceQueryService.updatePerformance(performanceId, createPerformanceDto()))
			.isInstanceOf(PerformanceNotFoundException.class);
	}

	@Test
	@DisplayName("공연 수정 실패: 수정된 공연 시작 일이 현재일 보다 이전 날짜이면 예외 발생")
	void updateWrongPerformanceStartDateException() {
		//given
		Long performanceId = 1L;
		PerformanceDto updateDto = createPerformanceDto("1990-01-01", "2023-04-01");

		//then
		assertThatThrownBy(() -> performanceQueryService.updatePerformance(performanceId, updateDto))
			.isInstanceOf(InvalidPerformanceDateException.class);
	}

	@Test
	@DisplayName("공연 수정 실패: 수정된 공연 시작 일이 공연 종료일 보다 나중이면 예외 발생")
	void updateWrongPerformanceScheduleException() {
		//given
		Long performanceId = 1L;
		PerformanceDto updateDto = createPerformanceDto("2023-05-01", "2023-04-01");

		//then
		assertThatThrownBy(() -> performanceQueryService.updatePerformance(performanceId, updateDto))
			.isInstanceOf(InvalidPerformanceDateException.class);
	}

	private PerformanceDto createPerformanceDto() {
		return PerformanceTestDataFactory.createPerformanceDto();
	}

	private Performance createPerformance() {
		return PerformanceTestDataFactory.createPerformance();
	}

	private PerformanceDto createPerformanceDto(String start, String end) {
		return PerformanceTestDataFactory.createPerformanceDto(start, end);
	}
}