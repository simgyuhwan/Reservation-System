package com.reservation.member.application.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.reservation.member.domain.Member;
import com.reservation.member.dto.response.MemberInfoDto;
import com.reservation.member.global.factory.MemberTestDataFactory;

/**
 * MemberInfoDtoMapperTest.java
 *
 * @author sgh
 * @since 2023.03.24
 */
class MemberInfoDtoMapperTest {
	private MemberInfoDtoMapper mapper = Mappers.getMapper(MemberInfoDtoMapper.class);

	@Test
	@DisplayName("MemberInfoDtoMapper 테스트 : toDTO 테스트")
	void toDtoTest() {
		//given
		Member member = MemberTestDataFactory.createMember();

		//when
		MemberInfoDto result = mapper.toDto(member);

		//then
		assertThat(result.getUserId()).isEqualTo(member.getUserId());
		assertThat(result.getUsername()).isEqualTo(member.getUsername());
		assertThat(result.getAddress()).isEqualTo(member.getAddress());
	}

	@Test
	@DisplayName("MemberInfoDtoMapper 테스트 : toEntity 테스트")
	void toEntityTest() {
		//given
		MemberInfoDto memberInfoDto = MemberTestDataFactory.createMemberInfoDto();

		//when
		Member result = mapper.toEntity(memberInfoDto);

		//then
		assertThat(result.getUserId()).isEqualTo(memberInfoDto.getUserId());
		assertThat(result.getUsername()).isEqualTo(memberInfoDto.getUsername());
		assertThat(result.getAddress()).isEqualTo(memberInfoDto.getAddress());
	}
}