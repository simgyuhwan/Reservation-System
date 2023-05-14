package com.sim.member.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sim.member.api.MemberController;
import com.sim.member.api.SignUpController;
import com.sim.member.dto.response.ErrorResponse;
import com.sim.member.memberdomain.error.DuplicateMemberException;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.error.MemberNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * MemberControllerAdvice.java
 * 회원 컨트롤러 Advice
 *
 * @author sgh
 * @since 2023.03.17
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {MemberController.class, SignUpController.class})
public class MemberControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> signUpBindingException(MethodArgumentNotValidException e) {
		log.error("SignUp failed due to invalid input value: {}", e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.bindError(ErrorCode.SIGNUP_INPUT_VALUE_INVALID,
			e.getFieldErrors());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(DuplicateMemberException.class)
	public ResponseEntity<ErrorResponse> duplicationMemberException(DuplicateMemberException e) {
		log.error("SignUp failed due to duplication Member error : {}", e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.DUPLICATE_MEMBER_ID_VALUE);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponse> memberNotFoundException(MemberNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.MEMBER_NOT_FOUND);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(InvalidUserIdException.class)
	public ResponseEntity<ErrorResponse> invalidUserIdException(InvalidUserIdException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.INVALID_USER_ID_VALUE);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

}
