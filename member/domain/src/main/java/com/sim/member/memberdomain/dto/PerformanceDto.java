package com.sim.member.memberdomain.dto;

import java.util.HashSet;
import java.util.Set;

import dto.Performance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
	private PerformanceDto(Long performanceId, Long memberId, String performanceName, String performanceStartDate,
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

	public static PerformanceDto from(Performance performance) {
		return PerformanceDto.builder()
			.performanceId(performance.getPerformanceId())
			.memberId(performance.getMemberId())
			.performanceName(performance.getPerformanceName())
			.performanceStartDate(performance.getPerformanceStartDate())
			.performanceEndDate(performance.getPerformanceEndDate())
			.performanceType(performance.getPerformanceType())
			.audienceCount(performance.getAudienceCount())
			.price(performance.getPrice())
			.contactPhoneNum(performance.getContactPhoneNum())
			.contactPersonName(performance.getContactPersonName())
			.performanceInfo(performance.getPerformanceInfo())
			.performancePlace(performance.getPerformancePlace())
			.performanceTimes(performance.getPerformanceTimes())
			.build();
	}
}
