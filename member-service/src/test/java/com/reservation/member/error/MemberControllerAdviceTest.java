package com.reservation.member.error;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.reservation.member.dto.ErrorCode;
import com.reservation.member.dto.response.ErrorResponse;

@ExtendWith(MockitoExtension.class)
class MemberControllerAdviceTest {

	@InjectMocks
	private MemberControllerAdvice advice;

	@Test
	void duplicationMemberException() {
		// given
		DuplicateMemberException exception = new DuplicateMemberException("중복된 아이디가 존재합니다.");

		// when
		ResponseEntity<ErrorResponse> response = advice.duplicationMemberException(exception);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		ErrorResponse body = response.getBody();
		assertThat(body).isNotNull();
		assertThat(ErrorCode.DUPLICATE_MEMBER_ID_VALUE.getMessage()).isEqualTo(body.getMessage());
		assertThat(ErrorCode.DUPLICATE_MEMBER_ID_VALUE.getStatus()).isEqualTo(body.getStatus());
	}
}