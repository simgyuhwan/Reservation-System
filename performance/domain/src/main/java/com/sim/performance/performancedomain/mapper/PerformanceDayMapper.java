package com.sim.performance.performancedomain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.domain.PerformanceDay;
import com.sim.performance.performancedomain.dto.PerformanceDto;

/**
 * PerformanceDayMapper.java
 * PerformanceDay Mapper 클래스
 *
 * @author sgh
 * @since 2023.04.03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceDayMapper{
    PerformanceDayMapper INSTANCE = Mappers.getMapper(PerformanceDayMapper.class);

    default List<PerformanceDay> toPerformanceDays(PerformanceDto dto, Performance performance){
        return dto.toPerformanceDays(performance);
    }
}
