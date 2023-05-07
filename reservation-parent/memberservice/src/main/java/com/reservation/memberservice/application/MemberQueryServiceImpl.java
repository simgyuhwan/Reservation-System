package com.reservation.memberservice.application;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reservation.common.dto.PerformanceDto;
import com.reservation.common.error.ErrorMessage;
import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.common.client.PerformanceApiClient;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.dto.response.MemberPerformanceDto;
import com.reservation.memberservice.error.InvalidUserIdException;
import com.reservation.memberservice.error.MemberNotFoundException;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MemberQueryServiceImpl.java
 * 회원 조회 관련 서비스
 *
 * @author sgh
 * @since 2023.03.23
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {
	private final MemberRepository memberRepository;
	private final MemberInfoDtoMapper memberInfoDtoMapper;
	private final PerformanceApiClient performanceApiClient;

	@Override
	public MemberInfoDto findMemberById(Long memberId) {
		Member member = findById(memberId);
		return memberInfoDtoMapper.toDto(member);
	}

	@Override
	public MemberInfoDto findMemberByUserId(String userId) {
		Assert.hasText(userId, "user id must exist");
		Member member = findByUserId(userId);
		return memberInfoDtoMapper.toDto(member);
	}

	@CircuitBreaker(name = "performanceApi", fallbackMethod = "fallback")
	@Override
	public MemberPerformanceDto selectPerformancesById(Long memberId) {
		Assert.notNull(memberId, "memberId must not be null");
		Member member = findById(memberId);
		List<PerformanceDto> performances = performanceApiClient.getPerformanceByMemberId(memberId);
		return MemberPerformanceDto.of(member, performances);
	}

	private Member findByUserId(String userId) {
		return memberRepository.findByUserId(userId)
			.orElseThrow(() -> new InvalidUserIdException(ErrorMessage.INVALID_USER_ID, userId));
	}

	private Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(ErrorMessage.MEMBER_NOT_FOUND, memberId));
	}

	public MemberPerformanceDto fallback(Long memberId, Throwable ex) {
		log.error("CircuitBreaker is open. Failed to get performances by memberId : {}", memberId, ex);
		Member member = findById(memberId);
		return MemberPerformanceDto.of(member, Collections.emptyList());
	}
}
