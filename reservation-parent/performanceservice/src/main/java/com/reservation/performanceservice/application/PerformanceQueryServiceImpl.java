package com.reservation.performanceservice.application;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reservation.common.error.ErrorMessage;
import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.NoContentException;
import com.reservation.performanceservice.error.PerformanceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PerformanceCommandServiceImpl.java
 * 공연 Command 관련 서비스 구현체
 *
 * @author sgh
 * @since 2023.04.06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService {
    private final PerformanceRepository performanceRepository;
    private final PerformanceDtoMapper performanceDtoMapper;

    @Override
    public List<PerformanceDto> selectPerformances(Long memberId) {
        List<Performance> performances = findByUserId(memberId);
        return performances.stream()
            .map(performanceDtoMapper::toDto)
            .collect(toList());
    }

    @Override
    public PerformanceDto selectPerformanceById(Long performanceId) {
        Performance performance = findById(performanceId);
        return performanceDtoMapper.toDto(performance);
    }

    private List<Performance> findByUserId(Long memberId) {
        List<Performance> performances = performanceRepository.findByMemberIdOrderByCreateDtDesc(memberId);
        if(performances.isEmpty()) {
            throw new NoContentException();
        }
        return performances;
    }

    private Performance findById(Long performanceId) {
        return performanceRepository.findById(performanceId)
            .orElseThrow(() -> new PerformanceNotFoundException(ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
    }
}
