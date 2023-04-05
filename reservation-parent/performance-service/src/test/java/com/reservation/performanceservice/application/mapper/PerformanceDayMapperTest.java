package com.reservation.performanceservice.application.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceRegistrationDto;
import com.reservation.performanceservice.factory.PerformanceTestDataFactory;

/**
 * PerformanceDayMapperTest.java
 * PerformanceDayMapper 테스트
 *
 * @author sgh
 * @since 2023.04.03
 */
class PerformanceDayMapperTest {
    private PerformanceDayMapper mapper = PerformanceDayMapper.INSTANCE;

    @Test
    @DisplayName("PerformanceDayMapper 테스트 : toPerformanceDays")
    void toEntityTest() {
        //given
        PerformanceRegistrationDto registerDto = PerformanceTestDataFactory.createPerformanceRegisterDto();

        //when
        List<PerformanceDay> performanceDays = mapper.toPerformanceDays(registerDto, null);

        //then
        assertThat(performanceDays).isNotEmpty();
    }

}