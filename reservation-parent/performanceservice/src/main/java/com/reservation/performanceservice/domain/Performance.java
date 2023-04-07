package com.reservation.performanceservice.domain;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.reservation.common.model.BaseEntity;
import com.reservation.performanceservice.dto.request.PerformanceDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Performance extends BaseEntity {

	@Id @Column(name = "performance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userId;

	private String performanceName;

	@Convert(converter = PerformanceTypeConverter.class)
	private PerformanceType performanceType;

	private Integer audienceCount;

	private Integer price;

	private String contactPhoneNum;

	private String contactPersonName;

	private String performanceInfo;

	private String performancePlace;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "performance")
	private List<PerformanceDay> performanceDays = new ArrayList<>();

	private Performance(String userId, String performanceName,PerformanceType performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum, String contactPersonName, String performanceInfo, String performancePlace, List<PerformanceDay> performanceDays) {
		this.userId = userId;
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

	public static Performance of(String userId, String performanceName, PerformanceType performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum, String contactPersonName, String performanceInfo, String performancePlace, List<PerformanceDay> performanceDays) {
		return new Performance(userId, performanceName,performanceType, audienceCount, price, contactPhoneNum, contactPersonName,
			performanceInfo, performancePlace, performanceDays);
	}

	public void setPerformanceDays(List<PerformanceDay> performanceDays) {
		this.performanceDays = performanceDays;
	}

	public void updateFromDto(PerformanceDto performanceDto) {
		this.performanceName = performanceDto.getPerformanceName();
		this.performanceType = PerformanceType.findByType(performanceDto.getPerformanceType());
		this.audienceCount = performanceDto.getAudienceCount();
		this.price = performanceDto.getPrice();
		this.contactPhoneNum = performanceDto.getContactPhoneNum();
		this.contactPersonName = performanceDto.getContactPersonName();
		this.performanceInfo = performanceDto.getPerformanceInfo();
		this.performancePlace = performanceDto.getPerformancePlace();

		List<PerformanceDay> updatePerformanceDays = performanceDto.toPerformanceDays(this);
		updatePerformanceDays(updatePerformanceDays);
	}

	public void updatePerformanceDays(List<PerformanceDay> updatePerformanceDays) {
		this.performanceDays.removeIf(performanceDay -> !updatePerformanceDays.contains(performanceDay));
		updatePerformanceDays.stream()
			.filter(performanceDay -> !this.performanceDays.contains(performanceDay))
			.forEach(this.performanceDays::add);
	}

}
