package com.sim.reservationservice.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reservation.common.type.PerformanceType;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.dto.request.PerformanceSearchDto;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;

import lombok.RequiredArgsConstructor;
import static com.sim.reservationservice.domain.QPerformanceInfo.performanceInfo;
import static com.sim.reservationservice.domain.QPerformanceSchedule.performanceSchedule;

/**
 * PerformanceCustomRepositoryImpl.java
 * 공연 관련 QueryDsl Repository
 *
 * @author sgh
 * @since 2023.04.20
 */
@Repository
@RequiredArgsConstructor
public class PerformanceCustomRepositoryImpl implements PerformanceCustomRepository{
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<List<PerformanceInfoDto>> selectPerformanceReservation(PerformanceSearchDto performanceSearchDto,
		Pageable pageable) {
		List<PerformanceInfo> performanceInfos = queryFactory.selectFrom(performanceInfo)
			.join(performanceInfo.performanceSchedules, performanceSchedule)
			.where(typeEq(performanceSearchDto.getType()),
				nameLike(performanceSearchDto.getName()),
				startDateAfter(performanceSearchDto.getStartDate()),
				endDateBefore(performanceSearchDto.getEndDate()),
				startTimeEq(performanceSearchDto.getStartTime()))
			.orderBy(performanceSchedule.startDate.asc(), performanceInfo.name.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// return new PageImpl<>(performanceInfos, pageable, performanceInfos.size());
		return null;
	}

	private BooleanExpression typeEq(String type){
		if(type != null) {
			return performanceInfo.type.eq(PerformanceType.findByType(type));
		}
		return null;
	}

	private BooleanExpression nameLike(String name) {
		if(name != null) {
			return performanceInfo.name.like("%" + name + "%");
		}
		return null;
	}

	private BooleanExpression startDateAfter(LocalDate startDate) {
		if(startDate != null) {
			return performanceSchedule.startDate.after(startDate);
		}
		return null;
	}

	private BooleanExpression endDateBefore(LocalDate endDate) {
		if(endDate != null) {
			return performanceSchedule.endDate.before(endDate);
		}
		return null;
	}

	private BooleanExpression startTimeEq(LocalTime startTime) {
		if(startTime != null) {
			return performanceSchedule.startTime.eq(startTime);
		}
		return null;
	}
}
