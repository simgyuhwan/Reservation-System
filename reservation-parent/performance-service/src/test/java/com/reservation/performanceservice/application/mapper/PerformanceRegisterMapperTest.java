package com.reservation.performanceservice.application.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceRegisterDto;
import com.reservation.performanceservice.factory.PerformanceTestDataFactory;

/**
 * PerformanceRegisterMapperTest.java
 * PerformanceRegister, Performance 매핑 테스트
 *
 * @author sgh
 * @since 2023.04.03
 */
class PerformanceRegisterMapperTest {
	private PerformanceRegisterMapper mapper = PerformanceRegisterMapper.INSTANCE;

	@Test
	@DisplayName("PerformanceRegisterMapper 테스트 : toEntity")
	void toEntityTest() {
		//given
		PerformanceRegisterDto registerDto = PerformanceTestDataFactory.createPerformanceRegisterDto();

		//when
		Performance result = mapper.toEntity(registerDto);

		//then
		assertThat(result.getUserId()).isEqualTo(registerDto.getUserId());
		assertThat(result.getAudienceCount()).isEqualTo(registerDto.getAudienceCount());
		assertThat(result.getPerformancePlace()).isEqualTo(registerDto.getPerformancePlace());
	}

}