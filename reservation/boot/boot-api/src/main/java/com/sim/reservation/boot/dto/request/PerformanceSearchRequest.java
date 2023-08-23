package com.sim.reservation.boot.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceSearchRequest.java
 * 예약 가능한 공연 정보를 조회하기 위한 Request
 *
 * @author sgh
 * @since 2023.05.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSearchRequest {
	@Schema(description = "조회 시작 날자", example = "yyyy-MM-dd")
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식이 잘못되었습니다.")
	private String startDate;

	@Schema(description = "조회 종료 날자", example = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String endDate;

	@Schema(description = "조회 시작 시간", example = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	private String startTime;

	@Schema(description = "조회 종료 시간", example = "HH:mm(보류)")
	@JsonFormat(pattern = "HH:mm")
	private String endTime;

	@Schema(description = "조회 이름", example = "오페라의 유령")
	private String name;

	@Schema(description = "조회 타입", example = "CONCERT")
	private String type;

	@Schema(description = "조회 장소", example = "홍대 1번 극장")
	private String place;

	@Builder
	public PerformanceSearchRequest(String startDate, String endDate, String startTime,
		String endTime,
		String name, String type, String place) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.type = type;
		this.place = place;
	}

	public PerformanceInfoSearchDto toSearchDto() {
		return PerformanceInfoSearchDto.builder()
			.startDate(parseLocalDate(startDate))
			.endDate(parseLocalDate(endDate))
			.startTime(parseLocalTime(startTime))
			.endTime(parseLocalTime(endTime))
			.name(name)
			.type(type)
			.place(place)
			.build();
	}
	public LocalDate parseLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}

	public LocalTime parseLocalTime(String time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return LocalTime.parse(time, formatter);
	}
}
