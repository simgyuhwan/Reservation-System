package com.reservation.performanceservice.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.reservation.common.error.ErrorMessage;
import com.reservation.common.error.EventNotFound;
import com.reservation.common.event.EventResult;
import com.reservation.common.event.payload.EventPayload;
import com.reservation.common.type.EventStatusTypes;
import com.reservation.performanceservice.dao.PerformanceEventResultRepository;
import com.reservation.performanceservice.domain.PerformanceEventResult;
import com.reservation.performanceservice.event.PerformanceEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceEventServiceImpl implements PerformanceEventService{
	private final PerformanceEventResultRepository performanceEventResultRepository;
	private final Gson gson;

	@Override
	public void saveEvent(PerformanceEvent<EventPayload> performanceEvent, EventStatusTypes status) {
		PerformanceEventResult eventResult = toEventResult(performanceEvent, status);
		saveEvent(eventResult);
	}

	@Override
	public void eventUpdate(EventResult eventResult) {
		PerformanceEventResult performanceEventResult = findById(eventResult.getId());
		updateEvent(eventResult, performanceEventResult);
	}

	private void updateEvent(EventResult eventResult, PerformanceEventResult performanceEventResult) {
		performanceEventResult.update(eventResult);
	}

	private PerformanceEventResult toEventResult(PerformanceEvent<EventPayload> performanceEvent, EventStatusTypes status) {
		return PerformanceEventResult.builder()
			.id(performanceEvent.getId())
			.status(status)
			.payload(gson.toJson(performanceEvent.getPayload()))
			.build();
	}

	private PerformanceEventResult findById(String id) {
		return performanceEventResultRepository.findById(id)
			.orElseThrow(() -> new EventNotFound(ErrorMessage.EVENT_NOT_FOUND, id));
	}

	private void saveEvent(PerformanceEventResult eventResult) {
		performanceEventResultRepository.save(eventResult);
	}

}
