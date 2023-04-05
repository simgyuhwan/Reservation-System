package com.reservation.performanceservice.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reservation.common.dto.ErrorResponse;
import com.reservation.common.error.ErrorCode;
import com.reservation.common.error.ErrorResponseFactory;
import com.reservation.performanceservice.api.PerformanceController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = {PerformanceController.class})
public class PerformanceControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("Performance register failed due to invalid input value: {}", e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.bindError(ErrorCode.PERFORMANCE_REGISTER_INPUT_VALUE_INVALID,
			e.getFieldErrors());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InvalidPerformanceDateException.class)
	public ResponseEntity<ErrorResponse> invalidPerformanceDateException(InvalidPerformanceDateException e) {
		log.error("Performance registration invalid date exception : {}", e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.INVALID_PERFORMANCE_DATE_VALUE);
		return ResponseEntity.badRequest().body(errorResponse);
	}
}
