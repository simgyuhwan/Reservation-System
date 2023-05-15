package com.sim.reservation.boot.error;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sim.reservation.boot.api.ReservationController;
import com.sim.reservation.boot.dto.response.ErrorResponse;
import com.sim.reservation.data.reservation.error.InternalException;
import com.sim.reservation.data.reservation.error.PerformanceInfoNotFoundException;
import com.sim.reservation.data.reservation.error.ReservationNotPossibleException;
import com.sim.reservation.data.reservation.error.SoldOutException;

import lombok.extern.slf4j.Slf4j;

/**
 * ReservationControllerAdvice.java
 * 공연 예약 api 에 대한 Advice
 *
 * @author sgh
 * @since 2023.04.26
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {ReservationController.class})
public class ReservationControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponseFactory.bindError(
			ErrorCode.INVALID_PERFORMANCE_RESERVATION_INFORMATION,
			e.getFieldErrors());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(SoldOutException.class)
	public ResponseEntity<ErrorResponse> soldOutException(SoldOutException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = createErrorResponse(ErrorCode.PERFORMANCE_SOLD_OUT_ERROR_MESSAGE);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(PerformanceInfoNotFoundException.class)
	public ResponseEntity<ErrorResponse> performanceInfoNotFoundException(PerformanceInfoNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = createErrorResponse(ErrorCode.NO_PERFORMANCE_INFORMATION_ERROR_MESSAGE);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(ReservationNotPossibleException.class)
	public ResponseEntity<ErrorResponse> reservationNotPossibleException(ReservationNotPossibleException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = createErrorResponse(ErrorCode.RESERVATION_NOT_POSSIBLE_ERROR_MESSAGE);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> noSuchElementException(NoSuchElementException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = createErrorResponse(ErrorCode.SCHEDULE_NOT_PART_OF_THE_PERFORMANCE_ERROR_MESSAGE);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(InternalException.class)
	public ResponseEntity<ErrorResponse> internalException(InternalException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = createErrorResponse(ErrorCode.RESERVATION_FAILED_DUE_TO_SERVER_FAILURE);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	private ErrorResponse createErrorResponse(ErrorCode errorCode) {
		return ErrorResponseFactory.from(errorCode);
	}
}
