package com.reservation.memberservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reservation.common.dto.ErrorResponse;
import com.reservation.common.error.ErrorCode;
import com.reservation.common.error.ErrorResponseFactory;
import com.reservation.memberservice.api.MemberController;
import com.reservation.memberservice.api.SignUpController;

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
		log.error("there are no matching members : {}", e.getUserId());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.NO_MEMBERS_MATCHED);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

}
