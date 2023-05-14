package com.sim.performance.performancedomain.service;

import java.time.LocalDate;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.performance.common.util.DateTimeUtils;
import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.dto.PerformanceCreateDto;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceStatusDto;
import com.sim.performance.performancedomain.dto.PerformanceUpdateDto;
import com.sim.performance.performancedomain.error.ErrorMessage;
import com.sim.performance.performancedomain.error.InvalidPerformanceDateException;
import com.sim.performance.performancedomain.error.PerformanceNotFoundException;
import com.sim.performance.performancedomain.event.PerformanceEvent;
import com.sim.performance.performancedomain.event.payload.PerformanceCreatedPayload;
import com.sim.performance.performancedomain.mapper.PerformanceDtoMapper;
import com.sim.performance.performancedomain.repository.PerformanceRepository;
import com.sim.performance.performancedomain.type.EventType;
import com.sim.performance.performancedomain.type.RegisterStatusType;

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
	public PerformanceStatusDto createPerformance(PerformanceCreateDto performanceCreateDto) {
		validatePerformanceDate(performanceCreateDto.getPerformanceStartDate(), performanceCreateDto.getPerformanceEndDate());
		Performance pendingPerformance = createPendingPerformance(performanceCreateDto);
		Performance performance = performanceRepository.save(pendingPerformance);

		eventPublisher.publishEvent(createPerformanceCreatedEvent(performance));

		return PerformanceStatusDto.requestComplete(performance.getId());
	}

	@Override
	public PerformanceDto updatePerformance(Long performanceId, PerformanceUpdateDto updateDto) {
		validatePerformanceDate(updateDto.getPerformanceStartDate(), updateDto.getPerformanceEndDate());
		Performance performance = findPerformanceById(performanceId);

		performance.updateFromDto(updateDto);
		return performanceDtoMapper.toDto(performance);
	}

	@Override
	public void performanceChangeStatus(Long performanceId, RegisterStatusType registerStatus) {
		Performance performance = findPerformanceById(performanceId);
		performance.changeStatus(registerStatus);
	}

	private Performance findPerformanceById(Long performanceId) {
		return performanceRepository.findById(performanceId)
			.orElseThrow(() -> new PerformanceNotFoundException(
				ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
	}

	private void validatePerformanceDate(String startDate, String endDate) {
		LocalDate start = DateTimeUtils.stringToLocalDate(startDate);
		LocalDate end = DateTimeUtils.stringToLocalDate(endDate);

		if(end.isBefore(start)) {
			throw new InvalidPerformanceDateException(ErrorMessage.PERFORMANCE_END_DATE_BEFORE_START_DATE);
		}

		if(start.isBefore(LocalDate.now())) {
			throw new InvalidPerformanceDateException(ErrorMessage.PERFORMANCE_START_DATE_IN_THE_PAST);
		}
	}

	private PerformanceEvent createPerformanceCreatedEvent(Performance performance) {
		return PerformanceEvent.pending(EventType.PERFORMANCE_CREATED)
			.payload(() -> PerformanceCreatedPayload.from(performance));
	}

	@NotNull
	private Performance createPendingPerformance(PerformanceCreateDto performanceDto) {
		Performance performance = Performance.createPendingPerformance(performanceDto);

		performance.getPerformanceDays().forEach(day -> day.setPerformance(performance));

		return performance;
	}
}
