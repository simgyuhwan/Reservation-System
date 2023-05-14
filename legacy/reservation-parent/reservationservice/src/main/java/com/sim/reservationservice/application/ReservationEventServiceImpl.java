package com.sim.reservationservice.application;

import org.jetbrains.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.reservation.common.error.ErrorMessage;
import com.reservation.common.event.DefaultEvent;
import com.reservation.common.event.EventResult;
import com.reservation.common.event.payload.Payload;
import com.reservation.common.event.payload.PerformanceCreatedPayload;
import com.sim.reservationservice.dao.EventStatusRepository;
import com.sim.reservationservice.domain.EventStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ReservationEventServiceImpl.java
 * 예약 이벤트 서비스 구현 클래스
 *
 * @author sgh
 * @since 2023.05.11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationEventServiceImpl implements ReservationEventService{
	private final PerformanceSyncService performanceSyncService;
	private final EventStatusRepository eventStatusRepository;

	@Override
	public EventResult savePerformanceInfo(DefaultEvent<PerformanceCreatedPayload> event) {
		String eventId = event.getId();
		EventStatus eventStatus = findEventStatusById(eventId);

		if(eventStatus == null) {
			return savePerformanceInfoFromPayload(eventId, event.getPayload());
		}

		return getEventResultFromStatus(eventStatus);
	}

	@Nullable
	@Cacheable(value = "eventStatus", key = "#eventId")
	public EventStatus findEventStatusById(String eventId) {
		return eventStatusRepository.findById(eventId).orElse(null);
	}

	private EventResult savePerformanceInfoFromPayload(String eventId, Payload payload) {
		PerformanceCreatedPayload performancePayload = (PerformanceCreatedPayload) payload;
		boolean result = performanceSyncService.requestAndSavePerformanceInfo(performancePayload.getPerformanceId());
		if(result) {
			return saveSuccessEventAndResult(eventId);
		}
		return saveFailEventAndReturnResult(eventId);
	}

	private EventResult saveSuccessEventAndResult(String eventId) {
		return EventResult.success(eventId);
	}

	private EventResult saveFailEventAndReturnResult(String eventId) {
		return EventResult.fail(eventId, ErrorMessage.FAILURE_TO_REGISTER_PERFORMANCE_INFORMATION.getMessage());
	}

	private EventResult getEventResultFromStatus(EventStatus eventStatus) {
		String eventId = eventStatus.getId();
		if(eventStatus.isSuccess()) {
			return EventResult.success(eventId);
		}
		return EventResult.fail(eventId, eventStatus.getMessage());
	}
}
