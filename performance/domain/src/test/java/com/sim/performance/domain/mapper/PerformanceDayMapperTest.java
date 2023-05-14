package com.sim.performance.domain.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sim.performance.domain.factory.PerformanceDtoFactory;
import com.sim.performance.performancedomain.domain.PerformanceDay;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.mapper.PerformanceDayMapper;

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
        PerformanceDto registerDto = PerformanceDtoFactory.createPerformanceDto();

        //when
        List<PerformanceDay> performanceDays = mapper.toPerformanceDays(registerDto, null);

        //then
        assertThat(performanceDays).isNotEmpty();
    }

}