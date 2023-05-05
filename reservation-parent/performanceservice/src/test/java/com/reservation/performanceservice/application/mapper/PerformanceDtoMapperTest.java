package com.reservation.performanceservice.application.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.factory.PerformanceDtoFactory;
import com.reservation.performanceservice.factory.PerformanceFactory;

/**
 * PerformanceRegisterMapperTest.java
 * PerformanceRegister, Performance 매핑 테스트
 *
 * @author sgh
 * @since 2023.04.03
 */
class PerformanceDtoMapperTest {
	private PerformanceDtoMapper mapper = PerformanceDtoMapper.INSTANCE;

	@Test
	@DisplayName("Mapper 테스트 : toEntity")
	void toEntityTest() {
		//given
		PerformanceDto registerDto = PerformanceDtoFactory.createPerformanceDto();

		//when
		Performance result = mapper.toEntity(registerDto);

		//then
		assertThat(result.getMemberId()).isEqualTo(registerDto.getMemberId());
		assertThat(result.getAudienceCount()).isEqualTo(registerDto.getAudienceCount());
		assertThat(result.getPerformancePlace()).isEqualTo(registerDto.getPerformancePlace());
	}

	@Test
	@DisplayName("Mapper 테스트 : toEntity 시, PerformanceDays AfterMapping 변환 테스트")
	void afterMappingTest() {
		//given
		PerformanceDto registerDto = PerformanceDtoFactory.createPerformanceDto();

		//when
		Performance result = mapper.toEntity(registerDto);

		//then
		assertThat(result.getPerformanceDays()).isNotEmpty();
	}

	@Test
	@DisplayName("Mapper 테스트 : toDto")
	void updateMappingTest() {
		//given
		Performance performance = PerformanceFactory.createPerformance();

		//when
		PerformanceDto result = mapper.toDto(performance);

		//then
		assertThat(result.getPerformanceTimes().size()).isEqualTo(performance.getPerformanceDays().size());
	}
}