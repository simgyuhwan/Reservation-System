package com.sim.reservation.boot.error;

import java.util.List;

import org.springframework.validation.FieldError;

import com.sim.reservation.boot.dto.response.ErrorResponse;

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

	public static ErrorResponse from(int statusCode, String message) {
		return ErrorResponse.status(statusCode)
			.message(message)
			.create();
	}
}
