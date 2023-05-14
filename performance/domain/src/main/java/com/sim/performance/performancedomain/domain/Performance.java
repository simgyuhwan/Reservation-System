package com.sim.performance.performancedomain.domain;

import java.util.ArrayList;
import java.util.List;

import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceUpdateDto;
import com.sim.performance.performancedomain.type.PerformanceType;
import com.sim.performance.performancedomain.type.PerformanceTypeConverter;
import com.sim.performance.performancedomain.type.RegisterStatusType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceInfo.java
 * 공연 Entity
 *
 * @author sgh
 * @since 2023.04.03
 */
@Entity
@Getter @Table(name = "performance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance extends BaseEntity {

	@Id @Column(name = "performance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long memberId;

	private String performanceName;

	@Convert(converter = PerformanceTypeConverter.class)
	private PerformanceType performanceType;

	private Integer audienceCount;

	private Integer price;

	private String contactPhoneNum;

	private String contactPersonName;

	private String performanceInfo;

	private String performancePlace;

	@Enumerated(EnumType.STRING)
	private RegisterStatusType registrationStatus;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "performance")
	private List<PerformanceDay> performanceDays = new ArrayList<>();

	private Performance(Long memberId, String performanceName, PerformanceType performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum, String contactPersonName, String performanceInfo, String performancePlace, List<PerformanceDay> performanceDays) {
		this.memberId = memberId;
		this.performanceName = performanceName;
		this.performanceType = performanceType;
		this.audienceCount = audienceCount;
		this.price = price;
		this.contactPhoneNum = contactPhoneNum;
		this.contactPersonName = contactPersonName;
		this.performanceInfo = performanceInfo;
		this.performancePlace = performancePlace;
		this.performanceDays = performanceDays;
	}

	@Builder
	private Performance(Long memberId, String performanceName, PerformanceType performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum, String contactPersonName, String performanceInfo, String performancePlace, List<PerformanceDay> performanceDays, RegisterStatusType registrationStatus) {
		this(memberId, performanceName, performanceType,audienceCount,price,contactPhoneNum,contactPersonName,performanceInfo,performancePlace,performanceDays);
		this.registrationStatus = registrationStatus;
	}

	public static Performance of(Long memberId, String performanceName, PerformanceType performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum, String contactPersonName, String performanceInfo, String performancePlace, List<PerformanceDay> performanceDays) {
		return new Performance(memberId, performanceName, performanceType, audienceCount, price, contactPhoneNum, contactPersonName,
			performanceInfo, performancePlace, performanceDays);
	}

	public static Performance createPendingPerformance(PerformanceCreateDto performanceCreateDto) {
		return Performance.builder()
			.memberId(performanceCreateDto.getMemberId())
			.performanceName(performanceCreateDto.getPerformanceName())
			.performanceType(performanceCreateDto.getPerformanceType())
			.audienceCount(performanceCreateDto.getAudienceCount())
			.price(performanceCreateDto.getPrice())
			.contactPhoneNum(performanceCreateDto.getContactPhoneNum())
			.contactPersonName(performanceCreateDto.getContactPersonName())
			.performanceInfo(performanceCreateDto.getPerformanceInfo())
			.performancePlace(performanceCreateDto.getPerformancePlace())
			.performanceDays(performanceCreateDto.getPerformanceDays())
			.registrationStatus(RegisterStatusType.PENDING)
			.build();
	}
	//
	// public static Performance createCompletedPerformance(PerformanceDto performanceDto) {
	// 	return Performance.builder()
	// 		.memberId(performanceDto.getMemberId())
	// 		.performanceName(performanceDto.getPerformanceName())
	// 		.performanceType(performanceDto.getPerformanceType())
	// 		.audienceCount(performanceDto.getAudienceCount())
	// 		.price(performanceDto.getPrice())
	// 		.contactPhoneNum(performanceDto.getContactPhoneNum())
	// 		.contactPersonName(performanceDto.getContactPersonName())
	// 		.performanceInfo(performanceDto.getPerformanceInfo())
	// 		.performancePlace(performanceDto.getPerformancePlace())
	// 		.registrationStatus(RegisterStatusType.COMPLETED)
	// 		.build();
	// }

	public void setPerformanceDays(List<PerformanceDay> performanceDays) {
		this.performanceDays = performanceDays;
	}

	public void updateFromDto(PerformanceUpdateDto performanceUpdateDto) {
		this.performanceName = performanceUpdateDto.getPerformanceName();
		this.performanceType = performanceUpdateDto.getPerformanceType();
		this.audienceCount = performanceUpdateDto.getAudienceCount();
		this.price = performanceUpdateDto.getPrice();
		this.contactPhoneNum = performanceUpdateDto.getContactPhoneNum();
		this.contactPersonName = performanceUpdateDto.getContactPersonName();
		this.performanceInfo = performanceUpdateDto.getPerformanceInfo();
		this.performancePlace = performanceUpdateDto.getPerformancePlace();

		performanceUpdateDto.getPerformanceDays().forEach(day -> day.setPerformance(this));

		updatePerformanceDays(performanceUpdateDto.getPerformanceDays());
	}

	public void updatePerformanceDays(List<PerformanceDay> updatePerformanceDays) {
		this.performanceDays.removeIf(performanceDay -> !updatePerformanceDays.contains(performanceDay));
		updatePerformanceDays.stream()
			.filter(performanceDay -> !this.performanceDays.contains(performanceDay))
			.forEach(this.performanceDays::add);
	}

	public void changeStatus(RegisterStatusType registerStatusType) {
		this.registrationStatus = registerStatusType;
	}

}
