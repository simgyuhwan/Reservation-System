package com.sim.reservationservice.application.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.common.mapper.GenericMapper;
import com.sim.reservationservice.domain.PerformanceSchedule;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.dto.request.PerformanceDto;

/**
 * PerformanceInfoMapper.java
 *
 * @author sgh
 * @since 2023.04.18
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceInfoMapper extends GenericMapper<PerformanceDto, PerformanceInfo> {
	PerformanceInfoMapper INSTANCE = Mappers.getMapper(PerformanceInfoMapper.class);

	@Mapping(target = "isAvailable", constant = "true")
	@Mapping(target = "type", expression = "java(com.reservation.common.type.PerformanceType.findByType(dto.getPerformanceType()))")
	@Mapping(target = "name", source = "performanceName")
	@Mapping(target = "info", source = "performanceInfo")
	@Mapping(target = "place", source = "performancePlace")
	@Override
	PerformanceInfo toEntity(PerformanceDto dto);

	@AfterMapping
	default void mapPerformanceDates(PerformanceDto dto, @MappingTarget PerformanceInfo performanceInfo) {
		List<PerformanceSchedule> performanceSchedules = dto.toPerformanceSchedules(performanceInfo);
		performanceInfo.setPerformanceSchedules(performanceSchedules);
	}
}
