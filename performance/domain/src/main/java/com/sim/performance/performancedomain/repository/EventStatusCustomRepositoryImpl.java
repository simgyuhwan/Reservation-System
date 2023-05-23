package com.sim.performance.performancedomain.repository;

import static com.sim.performance.performancedomain.domain.QEventStatus.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sim.performance.event.core.type.EventStatusType;
import com.sim.performance.event.core.type.EventType;
import com.sim.performance.performancedomain.domain.EventStatus;

import lombok.RequiredArgsConstructor;

/**
 * EventStatus Querydsl Repository
 */
@Repository
@RequiredArgsConstructor
public class EventStatusCustomRepositoryImpl implements EventStatusCustomRepository{
	private final JPAQueryFactory queryFactory;

	/**
	 * 수정 이벤트가 존재하는지 확인
	 */
	@Override
	public boolean checkIfRePublishableUpdatedEventExists() {
		return queryFactory.selectFrom(eventStatus)
			.where(isRetryStatus()
				.and(isUpdateEvent()))
			.fetchOne() != null;
	}

	/**
	 * 재발행할 수정 이벤트 조회
	 */
	@Override
	public List<EventStatus> findRePublishableUpdatedEvents() {
		return queryFactory.selectFrom(eventStatus)
			.where(isRetryStatus()
				.and(isUpdateEvent()))
			.fetch();
	}

	private BooleanExpression isUpdateEvent() {
		return eventStatus.eventType.eq(EventType.PERFORMANCE_UPDATE);
	}

	private BooleanExpression isRetryStatus() {
		return eventStatus.status.eq(EventStatusType.RETRY);
	}

}
