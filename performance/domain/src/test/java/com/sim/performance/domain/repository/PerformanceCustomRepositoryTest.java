package com.sim.performance.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sim.performance.domain.config.QueryDslTestConfig;
import com.sim.performance.domain.factory.PerformanceCreateDtoFactory;
import com.sim.performance.domain.factory.PerformanceDtoFactory;
import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.domain.PerformanceDay;
import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.repository.PerformanceCustomRepository;
import com.sim.performance.performancedomain.repository.PerformanceRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

/**
 * PerformanceCustomRepositoryImplTest.java 공연 QueryDsl test
 *
 * @author sgh
 * @since 2023.05.11
 */
@DataJpaTest
@Transactional(readOnly = true)
@Import(QueryDslTestConfig.class)
class PerformanceCustomRepositoryTest {

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
        assertThat(findPerformance.getPerformanceName()).isEqualTo(
            registeredPerformance.getPerformanceName());
        assertThat(findPerformance.getPerformanceInfo()).isEqualTo(
            registeredPerformance.getPerformanceInfo());
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
        PerformanceCreateDto performanceCreateDto = createPerformanceCreateDto();
        Performance pendingPerformance = Performance.createPendingPerformance(performanceCreateDto);

        return pendingPerformance;
    }

    private Performance createRegisteredPerformance() {
        PerformanceDto performanceDto = PerformanceDtoFactory.createPerformanceDto();
        Performance completedPerformance = Performance.createCompletedPerformance(performanceDto);
        List<PerformanceDay> performanceDays = performanceDto.toPerformanceDays(
            completedPerformance);

        completedPerformance.setPerformanceDays(performanceDays);
        return completedPerformance;
    }

    private PerformanceCreateDto createPerformanceCreateDto() {
        return PerformanceCreateDtoFactory.createPerformanceCreateDto();
    }
}
