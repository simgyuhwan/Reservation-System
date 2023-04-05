package com.reservation.performanceservice.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.common.error.ErrorCode;
import com.reservation.common.util.DateTimeUtils;
import com.reservation.performanceservice.application.mapper.PerformanceDayMapper;
import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceDayRepository;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.InvalidPerformanceDateException;
import com.reservation.performanceservice.error.PerformanceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService {
	private final PerformanceDayRepository performanceDayRepository;
	private final PerformanceRepository performanceRepository;
	private final PerformanceDayMapper performanceDayMapper;
	private final PerformanceDtoMapper performanceDtoMapper;

	@Override
	public void createPerformance(PerformanceDto registrationDto) {
		validatePerformanceDate(registrationDto);
		performanceRepository.save(performanceDtoMapper.toEntity(registrationDto));
	}

	@Override
	public PerformanceDto updatePerformance(Long performanceId, PerformanceDto updateDto) {
		validatePerformanceDate(updateDto);
		Performance performance = getPerformanceById(performanceId);
		List<PerformanceDay> performanceDays = getPerformanceDays(performance);
		return updateDto;
	}

	private List<PerformanceDay> getPerformanceDays(Performance performance) {
		List<PerformanceDay> performanceDays = performanceDayRepository.findByPerformance(performance);
		if(performanceDays.isEmpty()) {
			throw new PerformanceNotFoundException(ErrorCode.PERFORMANCE_NOT_FOUND_MESSAGE.getMessage() + performance.getId());
		}
		return performanceDays;
	}

	private Performance getPerformanceById(Long performanceId) {
		return performanceRepository.findById(performanceId)
			.orElseThrow(() -> new PerformanceNotFoundException(
				ErrorCode.PERFORMANCE_DAY_NOT_FOUND_MESSAGE.getMessage() + performanceId));
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
