package com.sim.member.memberdomain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberDto;

/**
 * MemberInfoDtoMapper.java
 *
 * @author sgh
 * @since 2023.03.24
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberDtoMapper extends GenericMapper<MemberDto, Member> {
	MemberDtoMapper INSTANCE = Mappers.getMapper(MemberDtoMapper.class);
}
