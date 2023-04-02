package com.reservation.performances.application;

import org.springframework.stereotype.Service;

import com.reservation.performances.dto.request.PerformanceRegisterDto;
import com.reservation.performances.error.InvalidPerformanceDateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService {
	@Override
	public void registerPerformance(PerformanceRegisterDto registerDto) {
		throw new InvalidPerformanceDateException("종료일자가 시작일자의 정보가 알맞지 않습니다.");
	}
}
