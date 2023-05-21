package com.sim.reservation.data.reservation.repository;

import static com.sim.reservation.data.reservation.domain.QEventStatus.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.reservation.data.reservation.domain.EventStatus;
import com.sim.reservation.data.reservation.domain.QEventStatus;
import com.sim.reservation.data.reservation.type.EventStatusType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventStatusCustomRepositoryImpl implements EventStatusCustomRepository{
	private final JPAQueryFactory queryFactory;

	@Override
	public boolean isEventStatusFinalized(String eventId) {
		return queryFactory.selectFrom(eventStatus)
			.where(idEq(eventId)
				.and(isSuccessfulOrFailedEvent()))
			.fetchOne() != null;
	}

	@Override
	public Optional<EventStatus> findById(String eventId) {
		EventStatus eventStatus = queryFactory.selectFrom(QEventStatus.eventStatus)
			.where(idEq(eventId))
			.orderBy(QEventStatus.eventStatus.createDt.desc())
			.fetchOne();
		return Optional.ofNullable(eventStatus);
	}

	private BooleanExpression idEq(String eventId) {
		if(eventId != null) {
			return eventStatus.id.eq(eventId);
		}
		return null;
	}

	private BooleanExpression isSuccessfulOrFailedEvent() {
		return eventStatus.status.in(EventStatusType.FAILED, EventStatusType.SUCCESS);
	}

}
