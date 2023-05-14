package com.sim.performance.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ErrorResponse.java
 * 공통 에러 응답 DTO
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	private int status;
	private String message;
	private List<FieldError> errors;

	public ErrorResponse(Builder builder) {
		this.status = builder.status;
		this.message = builder.message;
		this.errors = builder.getErrors();
	}

	public static Builder status(int status) {
		return new Builder(status);
	}

	@Getter
	public static class Builder {
		private int status;
		private String message;
		private List<FieldError> errors;

		public Builder(int status) {
			this.status = status;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder errors(List<org.springframework.validation.FieldError> errors) {
			this.errors = errors.stream()
				.map(FieldError::new)
				.collect(Collectors.toList());
			return this;
		}

		public ErrorResponse create() {
			return new ErrorResponse(this);
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private Object value;
		private String reason;

		public FieldError(org.springframework.validation.FieldError error) {
			this.field = error.getField();
			this.value = error.getRejectedValue();
			this.reason = error.getDefaultMessage();
		}
	}
}
