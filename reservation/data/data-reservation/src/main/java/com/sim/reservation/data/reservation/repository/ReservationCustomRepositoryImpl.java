package com.sim.reservation.data.reservation.repository;

import org.springframework.stereotype.Repository;
import static com.sim.reservation.data.reservation.domain.QPerformanceInfo.*;
import static com.sim.reservation.data.reservation.domain.QPerformanceSchedule.*;
import static com.sim.reservation.data.reservation.domain.QReservation.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.reservation.data.reservation.dto.ReservationInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository{
	private final JPAQueryFactory queryFactory;

	@Override
	public ReservationInfo findReservationInfoById(Long reservationId) {
		return queryFactory
			.select(Projections.constructor(ReservationInfo.class,
				reservation.id,
				reservation.name,
				reservation.phoneNum,
				reservation.email,
				performanceInfo.name,
				performanceSchedule.startDate,
				performanceSchedule.startTime,
				performanceInfo.place,
				reservation.isEmailReceiveDenied,
				reservation.isSmsReceiveDenied))
			.from(reservation)
			.join(reservation.performanceSchedule, performanceSchedule)
			.join(performanceSchedule.performanceInfo, performanceInfo)
			.where(reservation.id.eq(reservationId))
			.fetchOne();
	}
}
