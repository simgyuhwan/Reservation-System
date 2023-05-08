package com.sim.reservationservice.application.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.common.dto.PerformanceDto;
import com.reservation.common.mapper.GenericMapper;
import com.reservation.common.util.DateTimeUtils;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.domain.PerformanceSchedule;

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
	@Mapping(target = "type", expression = "java(com.reservation.common.types.PerformanceType.findByType(dto.getPerformanceType()))")
	@Mapping(target = "name", source = "performanceName")
	@Mapping(target = "info", source = "performanceInfo")
	@Mapping(target = "place", source = "performancePlace")
	@Override
	PerformanceInfo toEntity(PerformanceDto dto);

	@AfterMapping
	default void afterMapping(PerformanceDto dto, @MappingTarget PerformanceInfo performanceInfo) {
		List<PerformanceSchedule> performanceSchedules = createPerformanceSchedules(dto, performanceInfo);
		performanceInfo.setPerformanceSchedules(performanceSchedules);
	}

	private List<PerformanceSchedule> createPerformanceSchedules(PerformanceDto dto, PerformanceInfo performanceInfo) {
		LocalDate startDate = stringToLocalDate(dto.getPerformanceStartDate());
		LocalDate endDate = stringToLocalDate(dto.getPerformanceEndDate());

		List<LocalTime> performanceLocalTimes = dto.getPerformanceLocalTimes();

		return performanceLocalTimes.stream()
			.map(time -> PerformanceSchedule.builder()
				.startDate(startDate)
				.endDate(endDate)
				.performanceInfo(performanceInfo)
				.startTime(time)
				.availableSeats(dto.getAudienceCount())
				.remainingSeats(dto.getAudienceCount())
				.isAvailable(performanceInfo.isAvailable())
				.build())
			.toList();
	}

	private LocalDate stringToLocalDate(String date) {
		return DateTimeUtils.stringToLocalDate(date);
	}
}
