package com.reservation.member.application.mapper;

import com.reservation.member.domain.Member;
import com.reservation.member.dto.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * SignUpRequestMapper.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.03.17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SignUpRequestMapper extends GenericMapper<SignUpRequest, Member> {
    SignUpRequestMapper INSTANCE = Mappers.getMapper(SignUpRequestMapper.class);
}
