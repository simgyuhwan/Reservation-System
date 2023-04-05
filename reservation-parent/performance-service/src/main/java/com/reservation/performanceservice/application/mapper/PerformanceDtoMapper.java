package com.reservation.performanceservice.application.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.common.mapper.GenericMapper;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceDto;

/**
 * PerformanceMapper.java
 *
 * @author sgh
 * @since 2023.04.03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceDtoMapper extends GenericMapper<PerformanceDto, Performance> {
	PerformanceDtoMapper INSTANCE = Mappers.getMapper(PerformanceDtoMapper.class);

	@Mapping(target = "performanceType", expression = "java(PerformanceType.findByType(dto.getPerformanceType()))")
	@Override
	Performance toEntity(PerformanceDto dto);

	@AfterMapping
	default void mapPerformanceDays(PerformanceDto dto, @MappingTarget Performance performance) {
		List<PerformanceDay> performanceDays = dto.toPerformanceDays(performance);
		performance.setPerformanceDays(performanceDays);
	}
}
