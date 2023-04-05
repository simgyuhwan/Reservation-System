package com.reservation.performances.application.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.performances.domain.Performance;
import com.reservation.performances.domain.PerformanceDay;
import com.reservation.performances.dto.request.PerformanceRegisterDto;

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

    default List<PerformanceDay> toPerformanceDays(PerformanceRegisterDto dto, Performance performance){
        return dto.toPerformanceDays(performance);
    }
}
