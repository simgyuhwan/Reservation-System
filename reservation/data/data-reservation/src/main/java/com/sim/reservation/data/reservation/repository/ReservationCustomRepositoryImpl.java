package com.sim.reservation.data.reservation.repository;

import org.springframework.stereotype.Repository;
import static com.sim.reservation.data.reservation.domain.QPerformanceInfo.*;
import static com.sim.reservation.data.reservation.domain.QPerformanceSchedule.*;
import static com.sim.reservation.data.reservation.domain.QReservation.*;

import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.reservation.data.reservation.dto.ReservationInfo;
import com.sim.reservation.data.reservation.type.ReservationStatusType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository{
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<ReservationInfo> findReservationInfoById(Long reservationId) {
		ReservationInfo reservationInfo = queryFactory
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
			.where(reservation.id.eq(reservationId)
				.and(reservation.status.ne(ReservationStatusType.CANCELLATION_COMPLETED)))
			.fetchOne();
		return Optional.ofNullable(reservationInfo);
	}
}
