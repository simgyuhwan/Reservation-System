package com.sim.reservationservice.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reservation.common.dto.ErrorResponse;
import com.reservation.common.error.ErrorCode;
import com.reservation.common.error.ErrorResponseFactory;
import com.sim.reservationservice.api.PerformanceInfoController;

import lombok.extern.slf4j.Slf4j;

/**
 * ReservationControllerAdvice.java
 * 공연 조회 관련 api Advice
 *
 * @author sgh
 * @since 2023.04.12
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {PerformanceInfoController.class})
public class PerformanceInfoControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.bindError(ErrorCode.RESERVATION_SEARCH_VALUE_INVALID,
			e.getFieldErrors());
		return ResponseEntity.badRequest().body(errorResponse);
	}

}
