package com.sim.reservationservice.error;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reservation.common.dto.ErrorResponse;
import com.reservation.common.error.ErrorCode;
import com.reservation.common.error.ErrorResponseFactory;
import com.sim.reservationservice.api.ReservationController;

import lombok.extern.slf4j.Slf4j;

/**
 * ReservationControllerAdvice.java
 * 예약 컨트롤러 Advice
 *
 * @author sgh
 * @since 2023.04.12
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = {ReservationController.class})
public class ReservationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = ErrorResponseFactory.bindError(ErrorCode.RESERVATION_SEARCH_VALUE_INVALID,
            e.getFieldErrors());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
