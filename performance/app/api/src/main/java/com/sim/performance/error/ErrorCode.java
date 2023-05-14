package com.sim.performance.error;

import lombok.Getter;

/**
 * ErrorCode.java
 * HTTP 에러 반환 메시지 및 코드
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
public enum ErrorCode {

	// performance
	INVALID_PERFORMANCE_DATE_VALUE("잘못된 공연 날짜 정보입니다.", 400),
	PERFORMANCE_REGISTER_INPUT_VALUE_INVALID("공연 등록 값이 올바르지 않습니다.", 400),
	PERFORMANCE_END_DATE_BEFORE_START_DATE("공연 종료 날짜가 공연 시작 날짜보다 이전입니다.", 400),
	PERFORMANCE_START_DATE_IN_THE_PAST("공연 시작 날짜가 이미 지난 날짜입니다.", 400),
	PERFORMANCE_DATE_FORMAT_IS_INCORRECT("공연 날짜 형식이 잘못되었습니다.", 400),
	WRONG_PHONE_NUMBER("핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx", 400),
	NO_REGISTERED_PERFORMANCE_INFORMATION("등록된 공연 정보가 없습니다.", 400),
	PERFORMANCE_DAY_NOT_FOUND_MESSAGE("해당 performanceId로 등록된 공연 날짜 정보가 없습니다. performanceId : ", 400),
	NOT_FOUND_PENDING_PERFORMANCE("해당 performanceId로 등록 대기 중인 공연 정보가 없습니다.", 400);

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
