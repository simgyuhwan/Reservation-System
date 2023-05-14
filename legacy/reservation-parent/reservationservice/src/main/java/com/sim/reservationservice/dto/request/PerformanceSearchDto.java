package com.sim.reservationservice.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceSearchDto.java
 * 공연 조회 조건 DTO
 *
 * @author sgh
 * @since 2023.04.12
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSearchDto {
	@Schema(description = "조회 시작 날자", example = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "공연 시작 날짜는 현재 이후여야 합니다.")
	private LocalDate startDate;

	@Schema(description = "조회 종료 날자", example = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "공연 종료 날짜는 현재 이후여야 합니다.")
	private LocalDate endDate;

	@Schema(description = "조회 시작 시간", example = "HH:mm")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@Schema(description = "조회 종료 시간", example = "HH:mm(보류)")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;

	@Schema(description = "조회 이름", example = "오페라의 유령")
	private String name;

	@Schema(description = "조회 타입", example = "CONCERT")
	private String type;

	@Schema(description = "조회 장소", example = "홍대 1번 극장")
	private String place;

	@Builder
	public PerformanceSearchDto(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
		String name, String type, String place) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.type = type;
		this.place = place;
	}
}
