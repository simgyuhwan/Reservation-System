package com.sim.reservation.data.reservation.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.reservation.data.reservation.domain.EventStatus;
import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.event.DefaultEvent;
import com.sim.reservation.data.reservation.event.EventResult;
import com.sim.reservation.data.reservation.event.payload.Payload;
import com.sim.reservation.data.reservation.event.payload.PerformanceCreatedPayload;
import com.sim.reservation.data.reservation.repository.EventStatusRepository;

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
@Transactional
@RequiredArgsConstructor
public class ReservationEventServiceImpl implements ReservationEventService{
	private final PerformanceInfoSyncService performanceInfoSyncService;
	private final EventStatusRepository eventStatusRepository;

	@Override
	public EventResult savePerformanceInfo(DefaultEvent<PerformanceCreatedPayload> event) {
		String eventId = event.getId();
		EventStatus eventStatus = findEventStatusById(eventId);

		if(eventStatus == null) {
			EventStatus saveEventStatus = saveEvent(EventStatus.createStartEvent(eventId));
			return savePerformanceInfoFromPayload(eventId, event.getPayload(), saveEventStatus);
		}

		return getEventResultFromStatus(eventStatus);
	}

	@Nullable
	@Cacheable(value = "eventStatus", key = "#eventId")
	public EventStatus findEventStatusById(String eventId) {
		return eventStatusRepository.findById(eventId).orElse(null);
	}

	private EventResult savePerformanceInfoFromPayload(String eventId, Payload payload, EventStatus eventStatus) {
		PerformanceCreatedPayload performancePayload = (PerformanceCreatedPayload) payload;
		boolean success = performanceInfoSyncService.requestAndSavePerformanceInfo(performancePayload.getPerformanceId());
		if(success) {
			eventStatus.changeSuccess();
			return saveSuccessEventAndResult(eventId);
		}
		eventStatus.changeFailed();
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

	private EventStatus saveEvent(EventStatus eventStatus) {
		return eventStatusRepository.save(eventStatus);
	}
}
