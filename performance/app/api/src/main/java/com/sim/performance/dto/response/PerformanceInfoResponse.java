package com.sim.performance.dto.response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;

import com.sim.performance.performancedomain.dto.PerformanceDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceInfoDto.java
 * Class 설명을 작성하세요.
 *
 * @author sgh
 * @since 2023.05.11
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceInfoResponse {
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
	private PerformanceInfoResponse(Long performanceId, Long memberId, String performanceName,
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

	public static PerformanceInfoResponse from(PerformanceDto performanceDto) {
		return PerformanceInfoResponse.builder()
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
			.performancePlace(performanceDto.getPerformancePlace())
			.performanceTimes(performanceDto.getPerformanceTimes())
			.build();
	}

	public static List<PerformanceInfoResponse> from(List<PerformanceDto> performanceDtos) {
		return performanceDtos.stream()
			.map(PerformanceInfoResponse::from)
			.toList();
	}
}
