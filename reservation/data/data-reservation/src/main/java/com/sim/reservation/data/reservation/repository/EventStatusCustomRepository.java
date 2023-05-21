package com.sim.reservation.data.reservation.repository;

import java.util.Optional;

import com.sim.reservation.data.reservation.domain.EventStatus;

/**
 * EventStatus QueryDsl 용 Repository
 */
public interface EventStatusCustomRepository {

	/**
	 * 이벤트가 처리되었는지 확인
	 *
	 * @return 처리가 되었으면 true, 아니면 false
	 */
	boolean isEventStatusFinalized(String eventId);

	/**
	 * 등록된 최신 이벤트 조회
	 */
	Optional<EventStatus> findById(String eventId);
}
