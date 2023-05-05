package com.reservation.performanceservice.event;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PerformanceCreatedEvent {
	private LocalDateTime timestamp;
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
	private PerformanceCreatedEvent(LocalDateTime timestamp, Long performanceId, Long memberId, String performanceName,
		String performanceStartDate, String performanceEndDate, String performanceType, Integer audienceCount,
		Integer price, String contactPhoneNum, String contactPersonName, String performanceInfo,
		String performancePlace, Set<String> performanceTimes) {
		this.timestamp = timestamp;
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

}
