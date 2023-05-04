package com.reservation.performanceservice.application;

import java.time.LocalDate;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.error.ErrorCode;
import com.reservation.common.error.ErrorMessage;
import com.reservation.common.util.DateTimeUtils;
import com.reservation.performanceservice.application.mapper.CreatedEventMapper;
import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.InvalidPerformanceDateException;
import com.reservation.performanceservice.error.PerformanceNotFoundException;

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
	private final CreatedEventMapper createdEventMapper;

	@Override
	public void createPerformance(PerformanceDto registrationDto) {
		validatePerformanceDate(registrationDto);
		Performance performance = performanceRepository.save(performanceDtoMapper.toEntity(registrationDto));
		eventPublisher.publishEvent(createdEventMapper.toDto(performance));
	}

	@Override
	public PerformanceDto updatePerformance(Long performanceId, PerformanceDto updateDto) {
		validatePerformanceDate(updateDto);
		Performance performance = getPerformanceById(performanceId);
		performance.updateFromDto(updateDto);
		return performanceDtoMapper.toDto(performance);
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
}
