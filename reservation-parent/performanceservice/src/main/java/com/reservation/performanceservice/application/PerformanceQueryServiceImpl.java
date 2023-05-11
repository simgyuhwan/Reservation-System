package com.reservation.performanceservice.application;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.reservation.common.error.ErrorMessage;
import com.reservation.performanceservice.application.mapper.PerformanceDtoMapper;
import com.reservation.performanceservice.dao.PerformanceCustomRepository;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.dto.response.PerformanceStatusDto;
import com.reservation.performanceservice.error.NoContentException;
import com.reservation.performanceservice.error.NotPendingPerformanceException;
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
    private final PerformanceDtoMapper performanceDtoMapper;
    private final PerformanceCustomRepository performanceCustomRepository;

    @Override
    public List<PerformanceDto> selectPerformances(Long memberId) {
        List<Performance> performances = findRegisteredPerformanceByMemberId(memberId);
        return performances.stream()
            .map(performanceDtoMapper::toDto)
            .collect(toList());
    }

    @Override
    public PerformanceDto selectPerformanceById(Long performanceId) {
        Performance performance = findPerformanceById(performanceId);
        return performanceDtoMapper.toDto(performance);
    }

    @Override
    public PerformanceDto selectPendingPerformanceById(Long performanceId) {
        Performance performance = findPendingPerformanceById(performanceId);
        return performanceDtoMapper.toDto(performance);
    }

    @Override
    public PerformanceStatusDto getPerformanceStatusByPerformanceId(Long performanceId) {
        Performance performance = findPerformanceById(performanceId);
        return null;
    }

    private List<Performance> findRegisteredPerformanceByMemberId(Long memberId) {
        List<Performance> performances = performanceCustomRepository.findRegisteredPerformancesByMemberId(memberId);
        if(performances.isEmpty()) {
            throw new NoContentException();
        }
        return performances;
    }

    private Performance findPerformanceById(Long performanceId) {
        return performanceCustomRepository.findPerformanceById(performanceId)
            .orElseThrow(() -> new PerformanceNotFoundException(ErrorMessage.PERFORMANCE_NOT_FOUND, performanceId));
    }

    private Performance findPendingPerformanceById(Long performanceId) {
        return performanceCustomRepository.findPendingPerformance(performanceId)
            .orElseThrow(() -> new NotPendingPerformanceException(ErrorMessage.NOT_PENDING_PERFORMANCE, performanceId));
    }
}
