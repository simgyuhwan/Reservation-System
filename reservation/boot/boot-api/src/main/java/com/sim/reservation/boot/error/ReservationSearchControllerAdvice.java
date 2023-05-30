package com.sim.reservation.boot.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sim.reservation.boot.api.ReservationSearchController;
import com.sim.reservation.boot.dto.response.ErrorResponse;
import com.sim.reservation.data.reservation.error.ReservationNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * ReservationControllerAdvice.java
 * 공연 조회 관련 api Advice
 *
 * @author sgh
 * @since 2023.04.12
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {ReservationSearchController.class})
public class ReservationSearchControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.bindError(ErrorCode.RESERVATION_SEARCH_VALUE_INVALID,
			e.getFieldErrors());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(ReservationNotFoundException.class)
	public ResponseEntity<ErrorResponse> reservationNotFoundException(ReservationNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.from(ErrorCode.RESERVATION_NOT_FOUND_MESSAGE);
		return ResponseEntity.badRequest().body(errorResponse);
	}
}
