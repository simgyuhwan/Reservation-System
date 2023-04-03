package com.reservation.performances.dto.request;

import java.util.HashSet;
import java.util.Set;

import com.reservation.performances.error.ErrorCode;
import com.reservation.performances.error.InvalidPerformanceDateException;

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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceRegisterDto {
	@NotBlank(message = "등록자 정보는 반드시 필요합니다.")
	@Size(min = 2, max = 15, message = "등록자의 길이는 최소 2자리에서 15자리 이하입니다.")
	private String register;

	@NotBlank(message = "공연 시작 날짜는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "공연 날짜 형식이 잘못되었습니다. ex) '2024-01-01'")
	private String performanceStartDt;

	@NotBlank(message = "공연 종료 날짜는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "공연 날짜 형식이 잘못되었습니다. ex) '2024-01-01'")
	private String performanceEndDt;

	@NotBlank(message = "공연 종류는 반드시 입력해야 합니다. ex) 액션, 로맨스, 기타 등")
	private String performanceType;

	@NotNull(message = "관객 수는 반드시 입력해야 합니다.")
	@Min(value = 10, message = "관객 수는 반드시 10명 이상이어야 합니다.")
	private Integer audienceCount;

	@NotNull(message = "공연 가격 정보는 반드시 입력해야 합니다.")
	@Min(value = 5000, message = "공연 가격은 최소 5000원 이상이어야 합니다.")
	private Integer price;

	@NotBlank(message = "담당 번호는 반드시 입력해야 합니다.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx")
	private String contactPhoneNum;

	@NotBlank(message = "담당자 이름은 반드시 입력해야 합니다.")
	private String contactPersonName;

	@NotBlank(message = "공연 소개 정보는 반드시 입력해야 합니다.")
	@Size(min = 1, max = 255, message = "공연 정보는 최대 255자입니다.")
	private String performanceInfo;

	@NotBlank(message = "공연 장소는 반드시 입력해야 합니다.")
	private String performancePlace;

	@NotEmpty(message = "공연 시간은 최소 1가지 이상 입력해야 합니다.")
	private Set<String> performanceTimes = new HashSet<>();

	@Builder
	public PerformanceRegisterDto(String register, String performanceStartDt, String performanceEndDt,
		String performanceType, Integer audienceCount, Integer price, String contactPhoneNum, String contactPersonName,
		String performanceInfo, String performancePlace, Set<String> performanceTimes) {
		this.register = register;
		this.performanceStartDt = performanceStartDt;
		this.performanceEndDt = performanceEndDt;
		this.performanceType = performanceType;
		this.audienceCount = audienceCount;
		this.price = price;
		this.contactPhoneNum = contactPhoneNum;
		this.contactPersonName = contactPersonName;
		this.performanceInfo = performanceInfo;
		this.performancePlace = performancePlace;
		this.performanceTimes = performanceTimes;
	}

	public void dateValidation() {
		throw new InvalidPerformanceDateException(ErrorCode.PERFORMANCE_END_DATE_BEFORE_START_DATE.getMessage());
	}
}
