package com.reservation.performanceservice.application.mapper;

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

import com.reservation.common.mapper.GenericMapper;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.event.PerformanceCreatedEvent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreatedEventMapper extends GenericMapper<PerformanceCreatedEvent, Performance> {
	CreatedEventMapper INSTANCE = Mappers.getMapper(CreatedEventMapper.class);

	@Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "performanceId", source = "id")
	@Mapping(target = "performanceType", expression = "java(entity.getPerformanceType().getType())")
	@Mapping(target = "performanceTimes", source = "performanceDays", qualifiedByName = "mapTimesSet")
	@Mapping(target = "performanceStartDate", source = "performanceDays", qualifiedByName = "startDateString")
	@Mapping(target = "performanceEndDate", source = "performanceDays", qualifiedByName = "endDateString")
	@Override
	PerformanceCreatedEvent toDto(Performance entity);

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
