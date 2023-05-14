package com.sim.performance.performancedomain.dto;

import java.util.ArrayList;
import java.util.List;

import com.sim.performance.performancedomain.domain.PerformanceDay;
import com.sim.performance.performancedomain.type.PerformanceType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceUpdateDto {
	private Long performanceId;
	private Long memberId;
	private String performanceName;
	private String performanceStartDate;
	private String performanceEndDate;
	private PerformanceType performanceType;
	private Integer audienceCount;
	private Integer price;
	private String contactPhoneNum;
	private String contactPersonName;
	private String performanceInfo;
	private String performancePlace;
	private List<PerformanceDay> performanceDays = new ArrayList<>();

	@Builder
	public PerformanceUpdateDto(Long performanceId, Long memberId, String performanceName,
		String performanceStartDate, String performanceEndDate, PerformanceType performanceType, Integer audienceCount,
		Integer price, String contactPhoneNum, String contactPersonName, String performanceInfo,
		String performancePlace, List<PerformanceDay> performanceDays) {
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
		this.performanceDays = performanceDays;
	}
}
