package com.reservation.member.exception;

import com.reservation.member.api.MemberController;
import com.reservation.member.dto.ErrorCode;
import com.reservation.member.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * MemberControllerAdvice.java
 * 회원 컨트롤러 Advice
 *
 * @author sgh
 * @since 2023.03.17
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> signUpBindingException(MethodArgumentNotValidException e) {
        log.error("SignUp failed due to invalid input value: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindError(ErrorCode.SIGNUP_INPUT_VALUE_INVALID,
                e.getFieldErrors()));
    }

    private ErrorResponse bindError(ErrorCode errorCode, List<FieldError> errors) {
        return ErrorResponse
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(errors)
                .create();
    }
}
