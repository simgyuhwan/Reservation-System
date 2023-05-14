package com.sim.performance.performancedomain.error;

import lombok.Getter;

/**
 * ErrorMessage.java
 * 에러 메시지
 *
 * @author sgh
 * @since 2023.04.27
 */
@Getter
public enum ErrorMessage {
	PERFORMANCE_INFO_NOT_FOUND("PerformanceInfo 조회 실패, performanceInfoId : "),
	PERFORMANCE_SCHEDULE_NOT_FOUND("PerformanceSchedule 조회 실패, performanceScheduleId : "),
	PERFORMANCE_NOT_FOUND("Performance 조회 실패, performanceId : "),
	NOT_PENDING_PERFORMANCE("pending 상태인 공연 조회 실패, performanceId : "),
	PERFORMANCE_END_DATE_BEFORE_START_DATE("잘못된 공연 날짜 정보 : 공연 종료 날짜가 시작 날짜보다 먼저입니다."),
	PERFORMANCE_START_DATE_IN_THE_PAST("잘못된 공연 날짜 정보 : 시작 날짜가 이미 지난 날짜입니다."),
	NO_CONTENT_MESSAGE("회원 ID로 등록된 공연 정보가 없습니다. memberId : ");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
