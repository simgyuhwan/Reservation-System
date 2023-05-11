package com.reservation.performanceservice.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceCustomRepository;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.NoContentException;
import com.reservation.performanceservice.error.PerformanceNotFoundException;
import com.reservation.performanceservice.factory.PerformanceFactory;

/**
 * performanceQueryServiceTest.java
 *
 * @author sgh
 * @since 2023.04.06
 */
@ExtendWith(MockitoExtension.class)
class PerformanceQueryServiceTest {

	@InjectMocks
	private PerformanceQueryServiceImpl performanceQueryService;

	@Mock
	private PerformanceCustomRepository performanceCustomRepository;

	@Spy
	private PerformanceDtoMapper performanceDtoMapper = PerformanceDtoMapper.INSTANCE;

	@Nested
	@DisplayName("회원이 등록한 공연 전체 조회")
	class ViewAllPerformancesRegisteredByMemberTest {
		private final Long MEMBER_ID = PerformanceFactory.MEMBER_ID;

		@Test
		@DisplayName("공연 전체 조회 실패: 회원이 등록한 공연 정보가 없음, 예외 발생")
		void NoPerformanceInformationRegisteredByTheMemberException() {
			when(performanceCustomRepository.findRegisteredPerformancesByMemberId(MEMBER_ID)).thenReturn(Collections.emptyList());
			assertThatThrownBy(() -> performanceQueryService.selectPerformances(MEMBER_ID))
				.isInstanceOf(NoContentException.class);
		}

		@Test
		@DisplayName("공연 전체 조회 성공")
		void successfulViewingOfAllPerformances() {
			//given
			List<Performance> performances = createPerformanceList();
			when(performanceCustomRepository.findRegisteredPerformancesByMemberId(MEMBER_ID)).thenReturn(performances);

			//when
			List<PerformanceDto> result = performanceQueryService.selectPerformances(MEMBER_ID);

			//then
			assertThat(result.size()).isEqualTo(performances.size());
			assertThat(result.get(0).getMemberId()).isEqualTo(MEMBER_ID);
		}

		private List<Performance> createPerformanceList() {
			return PerformanceFactory.createPerformanceList();
		}
	}
	
	@Nested
	@DisplayName("공연 ID로 공연 상세 정보 조회")
	class ViewPerformanceDetailsByIdTest {
		private static final Long PERFORMANCE_ID = 1L;
		
		@Test
		@DisplayName("존재하지 않은 공연 ID로 예외 발생")
		void nonExistentPerformanceIDExceptionOccurred() {
			when(performanceCustomRepository.findPerformanceById(PERFORMANCE_ID)).thenReturn(Optional.empty());
			assertThatThrownBy(() -> performanceQueryService.selectPerformanceById(PERFORMANCE_ID))
				.isInstanceOf(PerformanceNotFoundException.class);
		}

		@Test
		@DisplayName("공연 ID로 공연 상세 조회 성공")
		void performanceDetailsInquirySuccess() {
			//given
			Performance performance = createPerformance();
			when(performanceCustomRepository.findPerformanceById(PERFORMANCE_ID)).thenReturn(Optional.of(performance));

			//when
			PerformanceDto performanceDto = performanceQueryService.selectPerformanceById(PERFORMANCE_ID);

			//then
			assertThat(performanceDto.getMemberId()).isEqualTo(performance.getMemberId());
			assertThat(performanceDto.getPerformancePlace()).isEqualTo(performance.getPerformancePlace());
			assertThat(performanceDto.getPerformanceName()).isEqualTo(performance.getPerformanceName());
		}
	}

	private Performance createPerformance() {
		return PerformanceFactory.createPerformance();
	}

}