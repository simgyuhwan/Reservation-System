package com.reservation.member.exception;

import com.reservation.member.dto.ErrorCode;
import com.reservation.member.dto.ErrorResponse;
import org.springframework.validation.FieldError;

import java.util.List;

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
