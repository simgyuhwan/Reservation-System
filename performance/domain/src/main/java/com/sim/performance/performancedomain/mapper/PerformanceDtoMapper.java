package com.sim.performance.performancedomain.mapper;

import static java.util.stream.Collectors.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.domain.PerformanceDay;
import com.sim.performance.performancedomain.dto.PerformanceDto;

/**
 * PerformanceMapper.java
 *
 * @author sgh
 * @since 2023.04.03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceDtoMapper extends GenericMapper<PerformanceDto, Performance> {
	PerformanceDtoMapper INSTANCE = Mappers.getMapper(PerformanceDtoMapper.class);

	@Mapping(target = "performanceType", expression = "java(com.sim.performance.performancedomain.type.PerformanceType.findByType(dto.getPerformanceType()))")
	@Override
	Performance toEntity(PerformanceDto dto);

	@Mapping(target = "performanceId", source = "id")
	@Mapping(target = "performanceType", expression = "java(entity.getPerformanceType().getType())")
	@Mapping(target = "performanceTimes", source = "performanceDays", qualifiedByName = "mapTimesSet")
	@Mapping(target = "performanceStartDate", source = "performanceDays", qualifiedByName = "startDateString")
	@Mapping(target = "performanceEndDate", source = "performanceDays", qualifiedByName = "endDateString")
	@Override
	PerformanceDto toDto(Performance entity);

	@Named("startDateString")
	default String mapStartDateString(List<PerformanceDay> performanceDays) {
		if(performanceDays.isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return performanceDays.stream()
			.map(PerformanceDay::getStart)
			.findFirst()
			.get()
			.format(formatter);
	}

	@Named("endDateString")
	default String mapEndDateString(List<PerformanceDay> performanceDays) {
		if(performanceDays.isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return performanceDays.stream()
			.map(PerformanceDay::getEnd)
			.findFirst()
			.get()
			.format(formatter);
	}

	@Named("mapTimesSet")
	default Set<String> mapTimesSetString(List<PerformanceDay> performanceDays) {
		if(performanceDays.isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return performanceDays.stream()
			.map(PerformanceDay::getTime)
			.map(time -> time.format(formatter))
			.collect(toSet());
	}

	@AfterMapping
	default void mapPerformanceDays(PerformanceDto dto, @MappingTarget Performance performance) {
		List<PerformanceDay> performanceDays = dto.toPerformanceDays(performance);
		performance.setPerformanceDays(performanceDays);
	}
}
