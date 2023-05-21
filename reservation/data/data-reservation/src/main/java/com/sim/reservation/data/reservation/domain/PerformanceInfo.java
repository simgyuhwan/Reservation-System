package com.sim.reservation.data.reservation.domain;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.sim.reservation.data.reservation.dto.PerformanceDto;
import com.sim.reservation.data.reservation.type.PerformanceType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
 * 공연 정보 Entity
 *
 * @author sgh
 * @since 2023.04.18
 */
@Getter
@Entity
@Table(name = "performance_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceInfo extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "performance_info_id")
	private Long id;
	private String name;
	private String info;
	private String place;
	private boolean isAvailable;
	private Integer price;
	private String contactPhoneNum;
	private String contactPersonName;
	private Long performanceId;

	@Enumerated(EnumType.STRING)
	private PerformanceType type;

	@OneToMany(orphanRemoval = true, mappedBy = "performanceInfo", cascade = CascadeType.ALL)
	private List<PerformanceSchedule> performanceSchedules;

	public void setPerformanceSchedules(List<PerformanceSchedule> performanceSchedules) {
		this.performanceSchedules = performanceSchedules;
	}

	public Optional<PerformanceSchedule> findScheduleById(Long performanceScheduleId) {
		return performanceSchedules.stream()
			.filter(s -> Objects.equals(s.getId(), performanceScheduleId))
			.findFirst();
	}

	@Builder
	public PerformanceInfo(Long id, String name, String info, String place, boolean isAvailable, Integer price,
		String contactPhoneNum, String contactPersonName, Long performanceId,
		PerformanceType type, List<PerformanceSchedule> performanceSchedules) {
		this.id = id;
		this.name = name;
		this.info = info;
		this.place = place;
		this.isAvailable = isAvailable;
		this.price = price;
		this.contactPhoneNum = contactPhoneNum;
		this.contactPersonName = contactPersonName;
		this.performanceId = performanceId;
		this.type = type;
		this.performanceSchedules = performanceSchedules;
	}

	public PerformanceInfo(String name, String info, String place, boolean isAvailable, Integer price,
		String contactPhoneNum, String contactPersonName, Long performanceId,
		PerformanceType type, List<PerformanceSchedule> performanceSchedules) {
		this.name = name;
		this.info = info;
		this.place = place;
		this.isAvailable = isAvailable;
		this.price = price;
		this.contactPhoneNum = contactPhoneNum;
		this.contactPersonName = contactPersonName;
		this.performanceId = performanceId;
		this.type = type;
		this.performanceSchedules = performanceSchedules;
	}

	public void updateFromDto(PerformanceDto performanceDto) {
		assert id.equals(performanceDto.getPerformanceId());

		name = performanceDto.getPerformanceName();
		info = performanceDto.getPerformanceInfo();
		place = performanceDto.getPerformancePlace();
		price = performanceDto.getPrice();
		contactPersonName = performanceDto.getContactPersonName();
		contactPhoneNum = performanceDto.getContactPhoneNum();
		type = PerformanceType.findByType(performanceDto.getPerformanceType());

		updatePerformanceSchedules(performanceDto);
	}

	private void updatePerformanceSchedules(PerformanceDto performanceDto) {
		performanceSchedules.clear();
		List<LocalTime> times = performanceDto.getPerformanceLocalTimes();
		for (LocalTime time : times) {
			PerformanceSchedule schedule = PerformanceSchedule.from(performanceDto, time);
			schedule.setPerformanceInfo(this);
			performanceSchedules.add(schedule);
		}
	}

}
