package com.sim.reservation.data.reservation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.sim.reservation.data.reservation.domain.Reservation;
import com.sim.reservation.data.reservation.dto.ReservationDto;

/**
 * ReservationInfoMapper.java
 *
 * @author sgh
 * @since 2023.04.27
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationInfoMapper extends GenericMapper<ReservationDto, Reservation> {
	ReservationInfoMapper INSTANCE = Mappers.getMapper(ReservationInfoMapper.class);
}
