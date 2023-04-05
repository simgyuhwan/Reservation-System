package com.reservation.performanceservice.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.common.mapper.GenericMapper;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceRegistrationDto;

/**
 * PerformanceMapper.java
 *
 * @author sgh
 * @since 2023.04.03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceRegisterMapper extends GenericMapper<PerformanceRegistrationDto, Performance> {
	PerformanceRegisterMapper INSTANCE = Mappers.getMapper(PerformanceRegisterMapper.class);

	@Mapping(target = "performanceType", expression = "java(PerformanceType.findByType(dto.getPerformanceType()))")
	@Override
	Performance toEntity(PerformanceRegistrationDto dto);
}
