package com.sim.performance.performancedomain.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sim.performance.common.util.DateTimeUtils;
import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.domain.PerformanceDay;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

	public List<PerformanceDay> toPerformanceDays(Performance performance) {
		LocalDate start = stringToLocalDate(this.performanceStartDate);
		LocalDate end = stringToLocalDate(this.performanceEndDate);

		return performanceTimes.stream()
			.map(time -> PerformanceDay.builder()
				.start(start)
				.end(end)
				.time(stringToLocalTime(time))
				.performance(performance)
				.build()
			)
			.toList();
	}

	private LocalDate stringToLocalDate(String date) {
		return DateTimeUtils.stringToLocalDate(date);
	}

	private LocalTime stringToLocalTime(String time) {
		return DateTimeUtils.stringToLocalTime(time);
	}


}
