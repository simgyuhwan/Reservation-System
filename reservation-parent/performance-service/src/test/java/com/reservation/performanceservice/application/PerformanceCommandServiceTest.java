package com.reservation.performanceservice.application;

import static com.reservation.performanceservice.factory.PerformanceTestDataFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.NoContentException;
import com.reservation.performanceservice.factory.PerformanceTestDataFactory;

/**
 * PerformanceCommandServiceTest.java
 *
 * @author sgh
 * @since 2023.04.06
 */
@ExtendWith(MockitoExtension.class)
class PerformanceCommandServiceTest {

	@InjectMocks
	private PerformanceCommandServiceImpl performanceCommandService;

	@Mock
	private PerformanceRepository performanceRepository;

	@Spy
	private PerformanceDtoMapper performanceDtoMapper = PerformanceDtoMapper.INSTANCE;

	@Test
	@DisplayName("공연 전체 조회 실패: 회원이 등록한 공연 정보가 없음, 예외 발생")
	void NoPerformanceInformationRegisteredByTheMemberException() {
		//given
		when(performanceRepository.findByUserIdOrderByCreateDtDesc(USER_ID)).thenReturn(Collections.EMPTY_LIST);

		//when, then
		assertThatThrownBy(() -> performanceCommandService.selectPerformances(USER_ID))
			.isInstanceOf(NoContentException.class);
	}

	@Test
	@DisplayName("공연 전체 조회 성공")
	void successfulViewingOfAllPerformances() {
		//given
		List<Performance> performances = createPerformanceList();
		when(performanceRepository.findByUserIdOrderByCreateDtDesc(USER_ID)).thenReturn(performances);

		//when
		List<PerformanceDto> result = performanceCommandService.selectPerformances(USER_ID);

		//then
		assertThat(result.size()).isEqualTo(performances.size());
		assertThat(result.get(0).getUserId()).isEqualTo(USER_ID);
	}
}