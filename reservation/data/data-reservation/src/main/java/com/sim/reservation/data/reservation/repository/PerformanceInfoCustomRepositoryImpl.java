package com.sim.reservation.data.reservation.repository;

import static com.sim.reservation.data.reservation.domain.QPerformanceInfo.*;
import static com.sim.reservation.data.reservation.domain.QPerformanceSchedule.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.dto.PerformanceInfoSearchDto;
import com.sim.reservation.data.reservation.type.PerformanceType;

import lombok.RequiredArgsConstructor;

/**
 * PerformanceCustomRepositoryImpl.java
 * 공연 관련 QueryDsl Repository
 *
 * @author sgh
 * @since 2023.04.20
 */
@Repository
@RequiredArgsConstructor
public class PerformanceInfoCustomRepositoryImpl implements PerformanceInfoCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Transactional
	@Override
	public Page<PerformanceInfoDto> selectPerformanceReservation(PerformanceInfoSearchDto performanceSearchDto,
		Pageable pageable) {
		List<PerformanceInfo> performanceInfos = queryFactory.selectFrom(performanceInfo)
			.join(performanceInfo.performanceSchedules, performanceSchedule)
			.where(typeEq(performanceSearchDto.getType()),
				nameLike(performanceSearchDto.getName()),
				startDateGoe(performanceSearchDto.getStartDate()),
				endDateLoe(performanceSearchDto.getEndDate()),
				startTimeEq(performanceSearchDto.getStartTime()),
				placeLike(performanceSearchDto.getPlace()))
			.orderBy(performanceSchedule.startDate.asc(), performanceInfo.name.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<PerformanceInfoDto> performanceInfoDtos = performanceInfos.stream()
			.map(PerformanceInfoDto::from).toList();

		return new PageImpl<>(performanceInfoDtos, pageable, performanceInfoDtos.size());
	}

	private BooleanExpression typeEq(String type) {
		if (type != null) {
			return performanceInfo.type.eq(PerformanceType.findByType(type));
		}
		return null;
	}

	private BooleanExpression nameLike(String name) {
		if (name != null) {
			return performanceInfo.name.like("%" + name + "%");
		}
		return null;
	}

	private BooleanExpression startDateGoe(LocalDate startDate) {
		if (startDate != null) {
			return performanceSchedule.startDate.goe(startDate);
		}
		return null;
	}

	private BooleanExpression endDateLoe(LocalDate endDate) {
		if (endDate != null) {
			return performanceSchedule.endDate.loe(endDate);
		}
		return null;
	}

	private BooleanExpression startTimeEq(LocalTime startTime) {
		if (startTime != null) {
			return performanceSchedule.startTime.eq(startTime);
		}
		return null;
	}

	private BooleanExpression placeLike(String place) {
		if (place != null) {
			return performanceInfo.place.like("%" + place + "%");
		}
		return null;
	}
}
