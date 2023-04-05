package com.reservation.memberservice.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.common.mapper.GenericMapper;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;

/**
 * MemberInfoDtoMapper.java
 *
 * @author sgh
 * @since 2023.03.24
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberInfoDtoMapper extends GenericMapper<MemberInfoDto, Member> {
	MemberInfoDtoMapper INSTANCE = Mappers.getMapper(MemberInfoDtoMapper.class);
}
