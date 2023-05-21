package com.sim.performance.performancedomain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.performance.event.dto.CreatedEventResultDto;
import com.sim.performance.event.dto.InternalEventDto;
import com.sim.performance.event.dto.UpdatedEventResultDto;
import com.sim.performance.event.payload.Payload;
import com.sim.performance.event.publisher.InternalEventPublisher;
import com.sim.performance.event.type.EventType;
import com.sim.performance.performancedomain.domain.EventStatus;
import com.sim.performance.performancedomain.repository.EventStatusCustomRepository;
import com.sim.performance.performancedomain.repository.EventStatusRepository;
import com.sim.performance.performancedomain.type.RegisterStatusType;

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
	private final EventStatusCustomRepository eventStatusCustomRepository;
	private final InternalEventPublisher internalEventPublisher;

	/**
	 * 이벤트 상태 저장
	 */
	@Override
	public void saveEvent(Payload payload, EventType eventType) {
		EventStatus eventStatus = EventStatus.from(payload, eventType);
		eventStatusRepository.save(eventStatus);
	}
	/**
	 * 공연 생성 이벤트 처리
	 */
	@Override
	public void handlePerformanceCreatedEventResult(CreatedEventResultDto createdEventResultDto) {
		EventStatus eventStatus = findEventById(createdEventResultDto.getId());
		if(eventStatus.isCompleted()) return;

		if(createdEventResultDto.isFailure()) {
			rollbackPerformanceRegistration(eventStatus);
			return;
		}

		completePerformanceRegistration(eventStatus);
	}

	/**
	 * 공연 수정 이벤트 처리
	 */
	@Override
	public void handlePerformanceUpdatedEventResult(UpdatedEventResultDto updatedEventResultDto) {
		EventStatus eventStatus = findEventById(updatedEventResultDto.getId());
		if(eventStatus.isCompleted()) return;

		if(updatedEventResultDto.isFailure()) {
			eventStatus.changeToRetry(updatedEventResultDto.getMessage());
			return;
		}

		completePerformanceRegistration(eventStatus);
	}

	/**
	 * 재시도 이벤트 재발행하기
	 */
	@Override
	public void rePublishPerformanceUpdateEvent() {
		if(!eventStatusCustomRepository.checkIfRePublishableUpdatedEventExists()) {
			return;
		}

		List<EventStatus> rePublishableUpdatedEvents = eventStatusCustomRepository.findRePublishableUpdatedEvents();
		rePublishableUpdatedEvents.forEach(event -> {
			internalEventPublisher.publishPerformanceUpdatedEvent(createInternalEventDto(event));
			event.changeToCompleted();
		});
	}

	private EventStatus findEventById(String eventId) {
		return eventStatusRepository.findById(eventId)
			.orElseThrow(EntityNotFoundException::new);
	}

	private void rollbackPerformanceRegistration(EventStatus eventStatus) {
		Long performanceId = eventStatus.getPerformanceId();
		performanceCommandService.performanceChangeStatus(performanceId, RegisterStatusType.FAILED);
		eventStatus.changeToFailed(eventStatus.getMessage());
	}

	private void completePerformanceRegistration(EventStatus eventStatus) {
		Long performanceId = eventStatus.getPerformanceId();
		performanceCommandService.performanceChangeStatus(performanceId, RegisterStatusType.COMPLETED);
		eventStatus.changeToCompleted();
	}

	private InternalEventDto createInternalEventDto(EventStatus eventStatus) {
		return InternalEventDto.builder()
			.id(UUID.randomUUID().toString())
			.performanceId(eventStatus.getPerformanceId())
			.build();
	}
}
