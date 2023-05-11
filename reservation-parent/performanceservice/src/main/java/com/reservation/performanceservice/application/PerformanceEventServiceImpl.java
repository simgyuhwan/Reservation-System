package com.reservation.performanceservice.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.event.EventResult;
import com.reservation.common.types.EventStatusType;
import com.reservation.performanceservice.dao.EventStatusRepository;
import com.reservation.performanceservice.domain.EventStatus;
import com.reservation.performanceservice.event.PerformanceEvent;
import com.reservation.performanceservice.types.RegisterStatusType;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceEventServiceImpl implements PerformanceEventService{
	private final EventStatusRepository eventStatusRepository;
	private final PerformanceCommandService performanceCommandService;

	@Override
	public void saveEvent(PerformanceEvent performanceEvent, EventStatusType status) {
		EventStatus eventStatus = EventStatus.from(performanceEvent, status);
		eventStatusRepository.save(eventStatus);
	}

	@Override
	public void handlePerformanceCreatedEventResult(EventResult eventResult) {
		EventStatus eventStatus = findEventById(eventResult.getId());
		if(eventStatus.isCompleted()) return;

		if(eventResult.isFailure()) {
			rollbackPerformanceRegistration(eventStatus);
		}

		completePerformanceRegistration(eventStatus);
	}

	private EventStatus findEventById(String eventId) {
		return eventStatusRepository.findById(eventId)
			.orElseThrow(EntityNotFoundException::new);
	}

	private void rollbackPerformanceRegistration(EventStatus eventStatus) {
		Long performanceId = eventStatus.getPerformanceId();
		performanceCommandService.performanceChangeStatus(performanceId, RegisterStatusType.FAILED);
		eventStatus.changeToFailed();
	}

	private void completePerformanceRegistration(EventStatus eventStatus) {
		Long performanceId = eventStatus.getPerformanceId();
		performanceCommandService.performanceChangeStatus(performanceId, RegisterStatusType.COMPLETED);
		eventStatus.changeToCompleted();
	}
}
