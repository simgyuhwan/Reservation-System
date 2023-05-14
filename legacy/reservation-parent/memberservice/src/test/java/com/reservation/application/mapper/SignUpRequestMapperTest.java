package com.reservation.application.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.reservation.factory.MemberFactory;
import com.reservation.factory.SignUpDtoFactory;
import com.reservation.memberservice.application.mapper.SignUpRequestMapper;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.request.SignUpDto;

/**
 * SignUpRequestMapperTest.java
 * 회원가입 DTO mapper Test
 *
 * @author sgh
 * @since 2023.03.24
 */
class SignUpRequestMapperTest {
	private SignUpRequestMapper mapper = Mappers.getMapper(SignUpRequestMapper.class);

	@Test
	@DisplayName("SignUpRequestMapper 테스트 : toDTO 테스트")
	void toDtoTest() {
		//given
		Member member = MemberFactory.createMember();

		//when
		SignUpDto result = mapper.toDto(member);

		//then
		assertThat(result.getUserId()).isEqualTo(member.getUserId());
		assertThat(result.getUsername()).isEqualTo(member.getUsername());
		assertThat(result.getAddress()).isEqualTo(member.getAddress());
	}

	@Test
	@DisplayName("SignUpRequestMapper 테스트 : toEntity 테스트")
	void toEntityTest() {
		//given
		SignUpDto signUpDto = createSignUpDto();

		//when
		Member result = mapper.toEntity(signUpDto);

		//then
		assertThat(result.getUserId()).isEqualTo(signUpDto.getUserId());
		assertThat(result.getUsername()).isEqualTo(signUpDto.getUsername());
		assertThat(result.getAddress()).isEqualTo(signUpDto.getAddress());
	}

	private SignUpDto createSignUpDto() {
		return SignUpDtoFactory.createSignUpDto();
	}
}