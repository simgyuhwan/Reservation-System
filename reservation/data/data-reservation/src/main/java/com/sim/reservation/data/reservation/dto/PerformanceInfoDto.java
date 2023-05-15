package com.sim.reservation.data.reservation.dto;

import java.util.ArrayList;
import java.util.List;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceInfoDto.java
 * 공연 정보가 담긴 DTO
 *
 * @author sgh
 * @since 2023.05.15
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceInfoDto {
	private String name;
	private String info;
	private String type;
	private String place;
	private boolean isAvailable;
	private String contactPhoneNum;
	private String contactPersonName;
	private Integer price;
	private List<PerformanceScheduleDto> schedules = new ArrayList<>();

	private Long performanceInfoId;

	@Builder
	public PerformanceInfoDto(Long performanceInfoId, String name, String info, String type, String place, boolean isAvailable,
		String contactPhoneNum, Integer price, String contactPersonName, List<PerformanceScheduleDto> schedules) {
		this.performanceInfoId = performanceInfoId;
		this.name = name;
		this.info = info;
		this.type = type;
		this.place = place;
		this.isAvailable = isAvailable;
		this.contactPhoneNum = contactPhoneNum;
		this.price = price;
		this.contactPersonName = contactPersonName;
		this.schedules = schedules;
	}

	public static PerformanceInfoDto from(PerformanceInfo performanceInfo) {
		List<PerformanceScheduleDto> performanceScheduleDtos = performanceInfo.getPerformanceSchedules().stream()
			.map(PerformanceScheduleDto::from)
			.toList();

		return PerformanceInfoDto.builder()
			.performanceInfoId(performanceInfo.getId())
			.name(performanceInfo.getName())
			.info(performanceInfo.getInfo())
			.type(performanceInfo.getType().getName())
			.place(performanceInfo.getPlace())
			.isAvailable(performanceInfo.isAvailable())
			.contactPhoneNum(performanceInfo.getContactPhoneNum())
			.contactPersonName(performanceInfo.getContactPersonName())
			.price(performanceInfo.getPrice())
			.schedules(performanceScheduleDtos)
			.build();
	}
}
