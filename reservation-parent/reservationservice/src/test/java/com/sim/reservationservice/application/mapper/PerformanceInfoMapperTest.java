package com.sim.reservationservice.application.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.dto.request.PerformanceDto;
import com.sim.reservationservice.factory.ReservationTestDataFactory;

/**
 * PerformanceInfoMapperTest.java
 * PerformanceDto, PerformanceInfo 매핑 테스트
 *
 * @author sgh
 * @since 2023.04.18
 */
class PerformanceInfoMapperTest {
	private PerformanceInfoMapper mapper = PerformanceInfoMapper.INSTANCE;

    @Test
	@DisplayName("Mapper 테스트 : toEntity")
	void toEntityTest() {
		//given
		PerformanceDto dto = ReservationTestDataFactory.createPerformanceDto();

		//when
		PerformanceInfo entity = mapper.toEntity(dto);

		//then
		System.out.println(entity);
	}
}