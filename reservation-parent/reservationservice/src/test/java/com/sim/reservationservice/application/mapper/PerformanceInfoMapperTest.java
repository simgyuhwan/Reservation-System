package com.sim.reservationservice.application.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.reservation.common.dto.PerformanceDto;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.factory.ReservationQueryDataFactory;

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
		PerformanceDto dto = ReservationQueryDataFactory.createPerformanceDto();

		//when
		PerformanceInfo entity = mapper.toEntity(dto);

		//then
		assertThat(entity.getName()).isEqualTo(dto.getPerformanceName());
		assertThat(entity.getInfo()).isEqualTo(dto.getPerformanceInfo());
		assertThat(entity.getPerformanceSchedules().size()).isEqualTo(dto.getPerformanceTimes().size());
	}
}