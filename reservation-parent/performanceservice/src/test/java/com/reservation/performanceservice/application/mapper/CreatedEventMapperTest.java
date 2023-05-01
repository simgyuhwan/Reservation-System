package com.reservation.performanceservice.application.mapper;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.event.PerformanceCreatedEvent;
import com.reservation.performanceservice.factory.PerformanceTestDataFactory;

class CreatedEventMapperTest {
	private CreatedEventMapper mapper = CreatedEventMapper.INSTANCE;

	@Test
	@DisplayName("CreatedEventMapper 테스트 : toDto")
	void toDto() {
		// given
		Performance performance = PerformanceTestDataFactory.createPerformance();

		// when
		PerformanceCreatedEvent result = mapper.toDto(performance);

		// then
		assertThat(result.getAudienceCount()).isEqualTo(performance.getAudienceCount());
		assertThat(result.getPerformanceName()).isEqualTo(performance.getPerformanceName());
		assertThat(result.getPerformanceTimes().size()).isEqualTo(performance.getPerformanceDays().size());
	}

	@Test
	@DisplayName("CreatedEventMapper 테스트 : toDto의 이벤트 발생 시간이 확인")
	void checkEventOccurrenceTime() {
		// given
		Performance performance = PerformanceTestDataFactory.createPerformance();

		// when
		PerformanceCreatedEvent result = mapper.toDto(performance);

		// then
		assertThat(result.getTimestamp()).isBeforeOrEqualTo(LocalDateTime.now());
	}
}