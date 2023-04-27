package com.reservation.common.error;

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
	// member
	SIGNUP_INPUT_VALUE_INVALID("입력 값이 올바르지 않습니다.", 400),
	DUPLICATE_MEMBER_ID_VALUE("중복된 회원 ID 입니다.", 409),
	NO_MEMBERS_MATCHED("일치하는 회원이 없습니다", 404),

	// performance
	INVALID_PERFORMANCE_DATE_VALUE("잘못된 공연 날짜 정보입니다.", 400),
	PERFORMANCE_REGISTER_INPUT_VALUE_INVALID("공연 등록 값이 올바르지 않습니다.", 400),
	PERFORMANCE_END_DATE_BEFORE_START_DATE("공연 종료 날짜가 공연 시작 날짜보다 이전입니다.", 400),
	PERFORMANCE_START_DATE_IN_THE_PAST("공연 시작 날짜가 이미 지난 날짜입니다.", 400),
	PERFORMANCE_DATE_FORMAT_IS_INCORRECT("공연 날짜 형식이 잘못되었습니다.", 400),
	WRONG_PHONE_NUMBER("핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx", 400),
	NO_REGISTERED_PERFORMANCE_INFORMATION("등록된 공연 정보가 없습니다.", 400),
	PERFORMANCE_NOT_FOUND_MESSAGE("해당 performanceId로 등록된 공연 정보가 없습니다. performanceId :", 400),
	PERFORMANCE_DAY_NOT_FOUND_MESSAGE("해당 performanceId로 등록된 공연 날짜 정보가 없습니다. performanceId : ", 400),

	// reservation
	RESERVATION_SEARCH_VALUE_INVALID("예약 가능한 공연 조회 값이 올바르지 않습니다.", 400),
	INVALID_PERFORMANCE_RESERVATION_INFORMATION("잘못된 공연 예약 정보입니다.", 400),
	PERFORMANCE_SOLD_OUT_ERROR_MESSAGE("죄송합니다. 해당 공연은 매진되어 예약이 불가능합니다.", 400),
	NO_PERFORMANCE_INFORMATION_ERROR_MESSAGE("죄송합니다. 해당 공연은 등록되어 있지 않습니다.", 404),
	RESERVATION_NOT_POSSIBLE_ERROR_MESSAGE("죄송합니다. 해당 공연은 예약이 불가능합니다.", 400),
	SCHEDULE_NOT_PART_OF_THE_PERFORMANCE_ERROR_MESSAGE("공연 일정을 다시 확인해주세요.", 400);

	private final String message;
	private final int status;

	ErrorCode(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
