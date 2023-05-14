package com.sim.performance.dto.response;

import java.util.HashSet;
import java.util.Set;

import com.sim.performance.performancedomain.dto.PerformanceDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceUpdateResponse {
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
	private PerformanceUpdateResponse(Long performanceId, Long memberId, String performanceName,
		String performanceStartDate, String performanceEndDate, String performanceType, Integer audienceCount,
		Integer price, String contactPhoneNum, String contactPersonName, String performanceInfo,
		String performancePlace, Set<String> performanceTimes) {
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

	public static PerformanceUpdateResponse from(PerformanceDto performanceDto) {
		return PerformanceUpdateResponse.builder()
			.performanceId(performanceDto.getPerformanceId())
			.memberId(performanceDto.getMemberId())
			.performanceName(performanceDto.getPerformanceName())
			.performanceStartDate(performanceDto.getPerformanceStartDate())
			.performanceEndDate(performanceDto.getPerformanceEndDate())
			.performanceType(performanceDto.getPerformanceType())
			.audienceCount(performanceDto.getAudienceCount())
			.price(performanceDto.getPrice())
			.contactPhoneNum(performanceDto.getContactPhoneNum())
			.contactPersonName(performanceDto.getContactPersonName())
			.performanceInfo(performanceDto.getPerformanceInfo())
			.performanceTimes(performanceDto.getPerformanceTimes())
			.build();
	}
}
