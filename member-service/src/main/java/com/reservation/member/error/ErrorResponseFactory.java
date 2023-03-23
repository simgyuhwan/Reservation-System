package com.reservation.member.error;

import java.util.List;

import org.springframework.validation.FieldError;

import com.reservation.member.dto.ErrorCode;
import com.reservation.member.dto.response.ErrorResponse;

public class ErrorResponseFactory {
	public static ErrorResponse bindError(ErrorCode errorCode, List<FieldError> errors) {
		return ErrorResponse.status(errorCode.getStatus())
			.message(errorCode.getMessage())
			.errors(errors)
			.create();
	}

	public static ErrorResponse from(ErrorCode errorCode) {
		return ErrorResponse.status(errorCode.getStatus())
			.message(errorCode.getMessage())
			.create();
	}
}
