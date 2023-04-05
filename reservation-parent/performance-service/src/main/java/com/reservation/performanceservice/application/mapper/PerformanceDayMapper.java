package com.reservation.performanceservice.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceRegistrationDto;

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

    default List<PerformanceDay> toPerformanceDays(PerformanceRegistrationDto dto, Performance performance){
        return dto.toPerformanceDays(performance);
    }
}
