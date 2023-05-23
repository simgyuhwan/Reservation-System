package com.sim.performance.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sim.performance.domain.factory.PerformanceCreateDtoFactory;
import com.sim.performance.domain.factory.PerformanceDtoFactory;
import com.sim.performance.domain.factory.PerformanceFactory;
import com.sim.performance.domain.factory.PerformanceUpdateDtoFactory;
import com.sim.performance.event.internal.InternalEventPublisher;
import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceUpdateDto;
import com.sim.performance.performancedomain.error.ErrorMessage;
import com.sim.performance.performancedomain.error.InvalidPerformanceDateException;
import com.sim.performance.performancedomain.error.PerformanceNotFoundException;
import com.sim.performance.performancedomain.mapper.PerformanceDtoMapper;
import com.sim.performance.performancedomain.repository.PerformanceRepository;
import com.sim.performance.performancedomain.service.PerformanceCommandServiceImpl;

/**
 * PerformanceQueryServiceTest.java
 *
 * @author sgh
 * @since 2023.04.03
 */
@ExtendWith(MockitoExtension.class)
class PerformanceCommandServiceTest {
	private static final Long MEMBER_ID = PerformanceDtoFactory.MEMBER_ID;

	@Mock
	private PerformanceRepository performanceRepository;

	@Mock
	private InternalEventPublisher internalEventPublisher;

	@Spy
	private PerformanceDtoMapper performanceDtoMapper = PerformanceDtoMapper.INSTANCE;

	@InjectMocks
	private PerformanceCommandServiceImpl performanceQueryService;

	@Nested
	@DisplayName("공연 등록")
	class PerformanceRegistrationTest {
		@Test
		@DisplayName("공연 등록 실패: 공연 종료일이 공연 시작일보다 앞에 있으면 예외 발생")
		void wrongPerformanceScheduleException() {
			//given
			PerformanceCreateDto performanceCreateDto = createPerformanceCreateDto("2023-05-01", "2023-04-01");

			//when, then
			assertThatThrownBy(() -> performanceQueryService.createPerformance(performanceCreateDto))
				.isInstanceOf(InvalidPerformanceDateException.class)
				.hasMessage(ErrorMessage.PERFORMANCE_END_DATE_BEFORE_START_DATE.getMessage());
		}

		@Test
		@DisplayName("공연 등록 실패: 공연 시작 일이 현재일 보다 이전 날짜이면 예외 발생")
		void wrongPerformanceStartDateException() {
			//given
			PerformanceCreateDto performanceCreateDto = createPerformanceCreateDto("1990-01-01", "2023-04-01");

			//when, then
			assertThatThrownBy(() -> performanceQueryService.createPerformance(performanceCreateDto))
				.isInstanceOf(InvalidPerformanceDateException.class)
				.hasMessage(ErrorMessage.PERFORMANCE_START_DATE_IN_THE_PAST.getMessage());
		}

		@Test
		@DisplayName("공연 등록 성공")
		void performanceRegisterSuccessTest() {
			//given
			when(performanceRepository.save(any())).thenReturn(createPerformance());
			PerformanceCreateDto performanceCreateDto = createPerformanceCreateDto();

			//when
			performanceQueryService.createPerformance(performanceCreateDto);

			//then
			then(performanceRepository).should().save(any(Performance.class));
		}
	}

	@Nested
	@DisplayName("공연 수정")
	class PerformanceCorrectionTest {
		@Test
		@DisplayName("공연 수정 실패: 등록된 공연 정보 없음, 예외 발생")
		void noRegisteredPerformanceInformationException() {
			//given
			Long performanceId = 1L;
			when(performanceRepository.findById(performanceId)).thenReturn(Optional.ofNullable(null));
			PerformanceUpdateDto performanceUpdateDto = createPerformanceUpdateDto();

			//then
			assertThatThrownBy(() -> performanceQueryService.updatePerformance(performanceId, performanceUpdateDto))
				.isInstanceOf(PerformanceNotFoundException.class);
		}

		@Test
		@DisplayName("공연 수정 실패: 수정된 공연 시작 일이 현재일 보다 이전 날짜이면 예외 발생")
		void updateWrongPerformanceStartDateException() {
			//given
			Long performanceId = 1L;
			PerformanceUpdateDto performanceUpdateDto = createPerformanceUpdateDto("1990-01-01", "2023-04-01");

			//then
			assertThatThrownBy(() -> performanceQueryService.updatePerformance(performanceId, performanceUpdateDto))
				.isInstanceOf(InvalidPerformanceDateException.class);
		}

		@Test
		@DisplayName("공연 수정 실패: 수정된 공연 시작 일이 공연 종료일 보다 나중이면 예외 발생")
		void updateWrongPerformanceScheduleException() {
			//given
			Long performanceId = 1L;
			PerformanceUpdateDto performanceUpdateDto = createPerformanceUpdateDto("2023-05-01", "2023-04-01");

			//then
			assertThatThrownBy(() -> performanceQueryService.updatePerformance(performanceId, performanceUpdateDto))
				.isInstanceOf(InvalidPerformanceDateException.class);
		}

		@Test
		@DisplayName("공연 수정 성공")
		void updateSuccessTest() {
			//given
			Long performanceId = 1L;
			when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(createPerformance()));
			PerformanceUpdateDto performanceUpdateDto = createPerformanceUpdateDto();

			//when
			PerformanceDto resultDto = performanceQueryService.updatePerformance(performanceId, performanceUpdateDto);

			//then
			assertThat(resultDto.getMemberId()).isEqualTo(performanceUpdateDto.getMemberId());
			assertThat(resultDto.getAudienceCount()).isEqualTo(performanceUpdateDto.getAudienceCount());
		}
	}

	private Performance createPerformance() {
		return PerformanceFactory.createPerformance();
	}

	private PerformanceUpdateDto createPerformanceUpdateDto() {
		return PerformanceUpdateDtoFactory.createPerformanceUpdateDto();
	}

	private PerformanceUpdateDto createPerformanceUpdateDto(String startDate, String endDate) {
		return PerformanceUpdateDtoFactory.createPerformanceUpdateDto(startDate, endDate);
	}

	private PerformanceCreateDto createPerformanceCreateDto() {
		return PerformanceCreateDtoFactory.createPerformanceCreateDto();
	}

	private PerformanceCreateDto createPerformanceCreateDto(String startDate, String endDate) {
		return PerformanceCreateDtoFactory.createPerformanceCreateDto(startDate, endDate);
	}
}