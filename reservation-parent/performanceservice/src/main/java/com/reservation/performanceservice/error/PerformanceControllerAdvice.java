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
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.bindError(ErrorCode.PERFORMANCE_REGISTER_INPUT_VALUE_INVALID,
			e.getFieldErrors());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InvalidPerformanceDateException.class)
	public ResponseEntity<ErrorResponse> invalidPerformanceDateException(InvalidPerformanceDateException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.from(400, e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(PerformanceNotFoundException.class)
	public ResponseEntity<ErrorResponse> performanceNotFoundException(PerformanceNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.NO_REGISTERED_PERFORMANCE_INFORMATION);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<ErrorResponse> noContentException(NoContentException e) {
		log.error("{} 이 등록한 공연이 없습니다.", e.getUserId());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.NO_REGISTERED_PERFORMANCE_INFORMATION);
		return ResponseEntity.badRequest().body(errorResponse);
	}
}
