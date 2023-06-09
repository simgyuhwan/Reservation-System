package com.sim.performance.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sim.performance.annotation.ValidPerformanceTimes;
import com.sim.performance.common.util.DateTimeUtils;
import com.sim.performance.performancedomain.domain.PerformanceDay;
import com.sim.performance.performancedomain.dto.PerformanceUpdateDto;
import com.sim.performance.performancedomain.type.PerformanceType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceUpdateRequest {
	private Long performanceId;

	@Schema(description = "회원 ID", example = "회원의 식별자 값")
	@NotNull(message = "memberId는 반드시 필요합니다.")
	private Long memberId;

	@Schema(description = "공연 이름", example = "오페라의 유령")
	@NotBlank(message = "공연 이름은 반드시 필요합니다.")
	private String performanceName;

	@Schema(description = "공연 시작 날짜", example = "2024-01-01")
	@NotBlank(message = "공연 시작 날짜는 반드시 입력 해야 합니다.")
	@Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "공연 날짜 형식이 잘못되었습니다. ex) '2024-01-01'")
	private String performanceStartDate;

	@Schema(description = "공연 종료 날짜", example = "2024-01-01")
	@NotBlank(message = "공연 종료 날짜는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "공연 날짜 형식이 잘못되었습니다. ex) '2024-01-01'")
	private String performanceEndDate;

	@Schema(description = "공연 종류", example = "THEATER")
	@NotBlank(message = "공연 종류는 반드시 입력해야 합니다. ex) THEATER, CONCERT, MUSICAL, OTHER")
	private String performanceType;

	@Schema(description = "관객 수", example = "100")
	@NotNull(message = "관객 수는 반드시 입력해야 합니다.")
	@Min(value = 10, message = "관객 수는 반드시 10명 이상이어야 합니다.")
	private Integer audienceCount;

	@Schema(description = "공연 가격", example = "10000")
	@NotNull(message = "공연 가격 정보는 반드시 입력해야 합니다.")
	@Min(value = 5000, message = "공연 가격은 최소 5000원 이상이어야 합니다.")
	private Integer price;

	@Schema(description = "담당자 번호", example = "010-1234-1234")
	@NotBlank(message = "담당 번호는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx")
	private String contactPhoneNum;

	@Schema(description = "담당자 이름", example = "홍길동")
	@NotBlank(message = "담당자 이름은 반드시 입력해야 합니다.")
	private String contactPersonName;

	@Schema(description = "공연 정보", example = "끝까지 간다....")
	@NotBlank(message = "공연 소개 정보는 반드시 입력해야 합니다.")
	@Size(min = 1, max = 255, message = "공연 정보는 최대 255자입니다.")
	private String performanceInfo;

	@Schema(description = "공연 장소", example = "홍대 시네마")
	@NotBlank(message = "공연 장소는 반드시 입력해야 합니다.")
	private String performancePlace;

	@Schema(description = "공연 시작 시간", example = "[\"14:00\", \"17:00\", \"20:00\"]")
	@NotEmpty(message = "공연 시간은 최소 1가지 이상 입력해야 합니다.")
	@ValidPerformanceTimes
	private Set<String> performanceTimes = new HashSet<>();

	@Builder
	public PerformanceUpdateRequest(Long performanceId, Long memberId, String performanceName, String performanceStartDate,
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

	public PerformanceUpdateDto toDto() {
		PerformanceType type = PerformanceType.findByType(performanceType);
		List<PerformanceDay> performanceDays = toPerformanceDays();

		return PerformanceUpdateDto.builder()
			.performanceId(performanceId)
			.memberId(memberId)
			.performanceName(performanceName)
			.performanceStartDate(performanceStartDate)
			.performanceEndDate(performanceEndDate)
			.performanceType(type)
			.audienceCount(audienceCount)
			.price(price)
			.contactPhoneNum(contactPhoneNum)
			.contactPersonName(contactPersonName)
			.performanceInfo(performanceInfo)
			.performancePlace(performancePlace)
			.performanceDays(performanceDays)
			.build();
	}

	public List<PerformanceDay> toPerformanceDays() {
		LocalDate start = stringToLocalDate(this.performanceStartDate);
		LocalDate end = stringToLocalDate(this.performanceEndDate);

		return performanceTimes.stream()
				.map(time -> PerformanceDay.builder()
					.start(start)
					.end(end)
					.time(stringToLocalTime(time))
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
