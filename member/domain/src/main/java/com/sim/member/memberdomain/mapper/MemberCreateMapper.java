package com.sim.member.memberdomain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberCreateRequestDto;

/**
 * SignUpRequestMapper.java
 *
 * @author sgh
 * @since 2023.03.17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberCreateMapper extends GenericMapper<MemberCreateRequestDto, Member> {
	MemberCreateMapper INSTANCE = Mappers.getMapper(MemberCreateMapper.class);
}
