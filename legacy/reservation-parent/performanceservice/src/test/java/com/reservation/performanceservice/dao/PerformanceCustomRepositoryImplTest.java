package com.reservation.performanceservice.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.performanceservice.config.QueryDslTestConfig;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.factory.PerformanceDtoFactory;

/**
 * PerformanceCustomRepositoryImplTest.java
 * 공연 QueryDsl test
 *
 * @author sgh
 * @since 2023.05.11
 */
@DataJpaTest
@Transactional(readOnly = true)
@Import(QueryDslTestConfig.class)
class PerformanceCustomRepositoryImplTest {
	private static final Long MEMBER_ID = PerformanceDtoFactory.MEMBER_ID;

	@Autowired
	private PerformanceCustomRepository performanceCustomRepository;

	@Autowired
	private PerformanceRepository performanceRepository;

	@Test
	@DisplayName("등록된 공연 정보 조회 확인")
	void registeredPerformancesInquiryTest() {
		//given
		Performance pendingPerformance = createPendingPerformance();
		Performance registeredPerformance = createRegisteredPerformance();
		List<Performance> performances = List.of(pendingPerformance, registeredPerformance);
		performanceRepository.saveAll(performances);

		//when
		List<Performance> registeredPerformances = performanceCustomRepository.findRegisteredPerformancesByMemberId(
			MEMBER_ID);

		//then
		assertThat(registeredPerformances.size()).isOne();

		Performance findPerformance = registeredPerformances.get(0);
		assertThat(findPerformance.getPerformanceName()).isEqualTo(registeredPerformance.getPerformanceName());
		assertThat(findPerformance.getPerformanceInfo()).isEqualTo(registeredPerformance.getPerformanceInfo());
	}

	@Test
	@DisplayName("공연 ID를 이용하여 미등록 확인")
	void confirmUnregisteredPerformanceTest() {
		//given
		Performance pendingPerformance = createPendingPerformance();
		Performance performance = performanceRepository.save(pendingPerformance);

		//when
		boolean result = performanceCustomRepository.isRegistrationCompleted(performance.getId());

		//then
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("공연 ID를 이용하여 등록 확인")
	void performanceRegistrationConfirmationTest() {
		//given
		Performance registeredPerformance = createRegisteredPerformance();
		Performance performance = performanceRepository.save(registeredPerformance);

		//when
		boolean result = performanceCustomRepository.isRegistrationCompleted(performance.getId());

		//then
		assertThat(result).isTrue();
	}

	private Performance createPendingPerformance() {
		PerformanceDto performanceDto = PerformanceDtoFactory.createPerformanceDto();
		Performance pendingPerformance = Performance.createPendingPerformance(performanceDto);
		List<PerformanceDay> performanceDays = performanceDto.toPerformanceDays(pendingPerformance);

		pendingPerformance.setPerformanceDays(performanceDays);
		return pendingPerformance;
	}

	private Performance createRegisteredPerformance() {
		PerformanceDto performanceDto = PerformanceDtoFactory.createPerformanceDto();
		Performance completedPerformance = Performance.createCompletedPerformance(performanceDto);
		List<PerformanceDay> performanceDays = performanceDto.toPerformanceDays(completedPerformance);

		completedPerformance.setPerformanceDays(performanceDays);
		return completedPerformance;
	}

}
