package com.reservation.member.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.member.domain.Member;
import com.reservation.member.dto.response.MemberInfoDto;

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
