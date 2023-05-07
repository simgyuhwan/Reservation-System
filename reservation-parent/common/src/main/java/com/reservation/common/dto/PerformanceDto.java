package com.reservation.common.dto;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.reservation.common.util.DateTimeUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceDto {
	private Long performanceId;
	private Long memberId;
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

	@Builder
	public PerformanceDto(Long performanceId, Long memberId, String performanceName, String performanceStartDate,
		String performanceEndDate, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum, String contactPersonName, String performanceInfo, String performancePlace,
		Set<String> performanceTimes) {
		this.performanceId = performanceId;
		this.memberId = memberId;
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

	public List<LocalTime> getPerformanceLocalTimes() {
		return performanceTimes.stream()
			.map(DateTimeUtils::stringToLocalTime)
			.toList();
	}
}
