package com.reservation.performanceservice.application;

import java.time.LocalDate;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.error.ErrorCode;
import com.reservation.common.error.ErrorMessage;
import com.reservation.common.util.DateTimeUtils;
import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.dto.response.PerformanceStatusDto;
import com.reservation.performanceservice.error.InvalidPerformanceDateException;
import com.reservation.performanceservice.error.PerformanceNotFoundException;
import com.reservation.performanceservice.event.payload.PerformanceCreatedPayload;
import com.reservation.performanceservice.event.PerformanceEvent;
import com.reservation.performanceservice.types.EventType;
import com.reservation.performanceservice.types.RegisterStatusType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceCommandServiceImpl implements PerformanceCommandService {
	private final PerformanceRepository performanceRepository;
	private final PerformanceDtoMapper performanceDtoMapper;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public PerformanceStatusDto createPerformance(PerformanceDto performanceDto) {
		validatePerformanceDate(performanceDto);
		Performance performance = performanceRepository.save(createPendingPerformance(performanceDto));

		eventPublisher.publishEvent(createPerformanceCreatedEvent(performance));

		return PerformanceStatusDto.requestComplete(performance.getId());
	}

	@Override
	public PerformanceDto updatePerformance(Long performanceId, PerformanceDto updateDto) {
		validatePerformanceDate(updateDto);
		Performance performance = getPerformanceById(performanceId);
		performance.updateFromDto(updateDto);
		return performanceDtoMapper.toDto(performance);
	}

	@Override
	public void performanceChangeStatus(Long performanceId, RegisterStatusType registerStatus) {
		Performance performance = getPerformanceById(performanceId);
		performance.changeStatus(registerStatus);
	}

	private Performance getPerformanceById(Long performanceId) {
		return performanceRepository.findById(performanceId)
			.orElseThrow(() -> new PerformanceNotFoundException(
				ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
	}

	private void validatePerformanceDate(PerformanceDto performanceDto) {
		LocalDate start = DateTimeUtils.stringToLocalDate(performanceDto.getPerformanceStartDate());
		LocalDate end = DateTimeUtils.stringToLocalDate(performanceDto.getPerformanceEndDate());

		if(end.isBefore(start)) {
			throw new InvalidPerformanceDateException(ErrorCode.PERFORMANCE_END_DATE_BEFORE_START_DATE.getMessage());
		}

		if(start.isBefore(LocalDate.now())) {
			throw new InvalidPerformanceDateException(ErrorCode.PERFORMANCE_START_DATE_IN_THE_PAST.getMessage());
		}
	}

	private PerformanceEvent createPerformanceCreatedEvent(Performance performance) {
		return PerformanceEvent.pending(EventType.PERFORMANCE_CREATED)
			.payload(() -> PerformanceCreatedPayload.from(performance));
	}

	@NotNull
	private Performance createPendingPerformance(PerformanceDto performanceDto) {
		Performance performance = Performance.createPendingPerformance(performanceDto);
		List<PerformanceDay> performanceDays = performanceDto.toPerformanceDays(performance);
		performance.setPerformanceDays(performanceDays);
		return performance;
	}
}
