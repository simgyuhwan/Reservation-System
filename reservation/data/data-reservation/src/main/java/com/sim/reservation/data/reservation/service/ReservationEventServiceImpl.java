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
import com.sim.reservation.data.reservation.event.payload.PerformanceEventPayload;
import com.sim.reservation.data.reservation.repository.EventStatusCustomRepository;
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
	private final EventStatusCustomRepository eventStatusCustomRepository;

	/**
	 * 공연 예약 정보 저장
	 * 처리된 이벤트라면 처리 결과 반환
	 * 처리되지 않은 이벤트라면 이벤트 처리
	 *
	 * @param defaultEvent 이벤트
	 * @return 이벤트 결과
	 */
	@Override
	public EventResult savePerformanceInfo(DefaultEvent<PerformanceEventPayload> event) {
		String eventId = event.getId();
		Payload payload = event.getPayload();

		if(isProcessedEvent(eventId)) {
			return getProcessedEventResult(eventId);
		}

		return processCreatedEvent(eventId, payload);
	}

	/**
	 * 공연 예약 정보 수정
	 *
	 * @param defaultEvent 이벤트
	 * @return 이벤트 결과
	 */
	@Override
	public EventResult updatePerformanceInfo(DefaultEvent<PerformanceEventPayload> event) {
		String eventId = event.getId();
		Payload payload = event.getPayload();

		if(isProcessedEvent(eventId)) {
			return getProcessedEventResult(eventId);
		}

		return processUpdatedEvent(eventId, payload);
	}

	/**
	 * 이미 처리된 이벤트인지 확인
	 */
	private boolean isProcessedEvent(String eventId) {
		return eventStatusCustomRepository.isEventStatusFinalized(eventId);
	}

	/**
	 * 이벤트 상태 조회
	 */
	@Nullable
	@Cacheable(value = "eventStatus", key = "#eventId")
	public EventStatus findEventStatusById(String eventId) {
		return eventStatusCustomRepository.findById(eventId).orElse(null);
	}

	/**
	 * 이미 처리가 완료된 이벤트의 경우, 저장된 결과 값 반환
	 */
	private EventResult getProcessedEventResult(String eventId) {
		EventStatus eventStatus = findEventStatusById(eventId);
		assert eventStatus != null;

		if(eventStatus.isSuccess()) {
			return EventResult.success(eventId);
		}
		return EventResult.fail(eventId, eventStatus.getMessage());
	}

	/**
	 *  공연 생성 이벤트 처리
	 */
	private EventResult processCreatedEvent(String eventId, Payload payload) {
		EventStatus eventStatus = saveEvent(EventStatus.createStartEvent(eventId));
		return savePerformanceInfoFromPayload(eventId, payload, eventStatus);
	}

	/**
	 * 공연 수정 이벤트 처리
	 */
	private EventResult processUpdatedEvent(String eventId, Payload payload) {
		EventStatus eventStatus = saveEvent(EventStatus.createStartEvent(eventId));
		return updatePerformanceInfoFromPayload(eventId, payload, eventStatus);
	}

	/**
	 * 이벤트 저장
	 */
	private EventStatus saveEvent(EventStatus eventStatus) {
		return eventStatusRepository.save(eventStatus);
	}

	/**
	 * payload 정보로 공연 정보를 조회하고 수정한 뒤 결과값 반환
	 */
	private EventResult updatePerformanceInfoFromPayload(String eventId, Payload payload, EventStatus eventStatus) {
		PerformanceEventPayload performancePayload = (PerformanceEventPayload) payload;
		boolean success = performanceInfoSyncService.requestAndUpdatePerformanceInfo(performancePayload.getPerformanceId());

		if(success) {
			return handleSuccess(eventStatus, eventId);
		}
		return handleFailure(eventStatus, eventId, ErrorMessage.FAILURE_TO_UPDATE_PERFORMANCE_INFORMATION);
	}

	/**
	 * payload 의 정보로 공연 정보를 조회하고 저장한 뒤 결과값 반환
	 */
	private EventResult savePerformanceInfoFromPayload(String eventId, Payload payload, EventStatus eventStatus) {
		PerformanceEventPayload performancePayload = (PerformanceEventPayload) payload;
		boolean success = performanceInfoSyncService.requestAndSavePerformanceInfo(performancePayload.getPerformanceId());

		if(success) {
			return handleSuccess(eventStatus, eventId);
		}

		return handleFailure(eventStatus, eventId, ErrorMessage.FAILURE_TO_REGISTER_PERFORMANCE_INFORMATION);
	}

	/**
	 * 이벤트 실패 값 반환
	 */
	private EventResult handleFailure(EventStatus eventStatus, String eventId,
		ErrorMessage failureToRegisterPerformanceInformation) {
		eventStatus.changeFailed();
		return EventResult.fail(eventId, failureToRegisterPerformanceInformation);
	}

	/**
	 * 이벤트 성공 값 반환
	 */
	private EventResult handleSuccess(EventStatus eventStatus, String eventId) {
		eventStatus.changeSuccess();
		return EventResult.success(eventId);
	}


}
