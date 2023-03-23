package com.reservation.member.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.reservation.member.domain.Member;
import com.reservation.member.dto.request.SignUpDto;

/**
 * SignUpRequestMapper.java
 *
 * @author sgh
 * @since 2023.03.17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignUpRequestMapper extends GenericMapper<SignUpDto, Member> {
	SignUpRequestMapper INSTANCE = Mappers.getMapper(SignUpRequestMapper.class);
}
