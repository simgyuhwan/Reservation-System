package com.sim.reservationservice.dto.request;

import static java.util.stream.Collectors.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.reservation.common.util.DateTimeUtils;
import com.sim.reservationservice.domain.PerformanceDate;
import com.sim.reservationservice.domain.PerformanceInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * PerformanceDto.java
 * 컨슈머로 받을 공연 DTO
 *
 * @author sgh
 * @since 2023.04.18
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceDto {
	private Long performanceId;
	private String userId;
	private String performanceName;
	private String performanceStartDate;
	private String performanceEndDate;
	private String performanceType;
	private Integer audienceCount;
	private Integer price;
	private String contactPhoneNum;
	private String contactPersonName;
	private String performanceInfo;
	private String performancePlace;
	private Set<String> performanceTimes = new HashSet<>();

	public List<PerformanceDate> toPerformanceDates(PerformanceInfo performanceInfo) {
		LocalDate start = DateTimeUtils.stringToLocalDate(this.performanceStartDate);
		LocalDate end = DateTimeUtils.stringToLocalDate(this.performanceEndDate);

		return performanceTimes.stream()
			.map(time -> PerformanceDate.builder()
				.startDate(start)
				.endDate(end)
				.startTime(DateTimeUtils.stringToLocalTime(time))
				.performanceInfo(performanceInfo)
				.build()
			).collect(toList());
	}

	@Builder
	public PerformanceDto(Long performanceId, String userId, String performanceName, String performanceStartDate,
		String performanceEndDate, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum, String contactPersonName, String performanceInfo, String performancePlace,
		Set<String> performanceTimes) {
		this.performanceId = performanceId;
		this.userId = userId;
		this.performanceName = performanceName;
		this.performanceStartDate = performanceStartDate;
		this.performanceEndDate = performanceEndDate;
		this.performanceType = performanceType;
		this.audienceCount = audienceCount;
		this.price = price;
		this.contactPhoneNum = contactPhoneNum;
		this.contactPersonName = contactPersonName;
		this.performanceInfo = performanceInfo;
		this.performancePlace = performancePlace;
		this.performanceTimes = performanceTimes;
	}
}
