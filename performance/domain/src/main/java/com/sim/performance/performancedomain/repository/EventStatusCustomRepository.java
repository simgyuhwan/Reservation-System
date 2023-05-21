package com.sim.performance.performancedomain.repository;

import java.util.List;

import com.sim.performance.performancedomain.domain.EventStatus;

/**
 * EventStatus Querydsl Repository
 */
public interface EventStatusCustomRepository {

	/**
	 * 수정 이벤트가 존재하는지 확인
	 */
	boolean checkIfRePublishableUpdatedEventExists();

	/**
	 * 재발행할 수정 이벤트 조회
	 */
	List<EventStatus> findRePublishableUpdatedEvents();
}
