package com.sim.reservation.boot.dto.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceInfoResponse.java
 * 공연 정보가 담긴 Response
 *
 * @author sgh
 * @since 2023.05.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceInfoResponse {
	@Schema(description = "공연 이름", example = "오페라의 유령")
	private String name;
	@Schema(description = "공연 정보", example = "즐거운 공연 정보")
	private String info;
	@Schema(description = "공연 타입", example = "콘서트")
	private String type;
	@Schema(description = "공연 장소", example = "홍대 1 극장")
	private String place;
	@Schema(description = "예약 가능 여부", example = "true")
	private boolean isAvailable;
	@Schema(description = "담당자 번호", example = "010-xxxx-xxxx")
	private String contactPhoneNum;
	@Schema(description = "담당자 이름", example = "홍길동")
	private String contactPersonName;
	@Schema(description = "공연 가격", example = "15000")
	private Integer price;
	@Schema(description = "공연 시작 시간", example = "[15:00]")
	private List<PerformanceScheduleResponse> schedules = new ArrayList<>();

	private Long performanceInfoId;
}
