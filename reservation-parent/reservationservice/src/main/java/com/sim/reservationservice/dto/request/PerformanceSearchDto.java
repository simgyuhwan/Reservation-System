package com.sim.reservationservice.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "공연 시작 날짜는 현재 이후여야 합니다.")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "공연 종료 날짜는 현재 이후여야 합니다.")
	private LocalDate endDate;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;

	private String name;

	private String type;

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
