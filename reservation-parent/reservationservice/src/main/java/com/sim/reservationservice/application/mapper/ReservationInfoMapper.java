package com.sim.reservationservice.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.common.mapper.GenericMapper;
import com.sim.reservationservice.domain.Reservation;
import com.sim.reservationservice.dto.response.ReservationInfoDto;

/**
 * ReservationInfoMapper.java
 *
 * @author sgh
 * @since 2023.04.27
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationInfoMapper extends GenericMapper<ReservationInfoDto, Reservation> {
	ReservationInfoMapper INSTANCE = Mappers.getMapper(ReservationInfoMapper.class);

}
