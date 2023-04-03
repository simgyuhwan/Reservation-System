package com.reservation.performances.application;

import org.springframework.stereotype.Service;

import com.reservation.performances.dto.request.PerformanceRegisterDto;
import com.reservation.performances.error.ErrorCode;
import com.reservation.performances.error.InvalidPerformanceDateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceQueryServiceImpl implements PerformanceQueryService {
	@Override
	public void registerPerformance(PerformanceRegisterDto registerDto) {
		validateRegisterDto(registerDto);

	}

	private void validateRegisterDto(PerformanceRegisterDto registerDto) {
		registerDto.dateValidation();
	}
}
