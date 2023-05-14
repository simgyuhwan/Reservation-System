package com.sim.member.memberdomain.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sim.member.clients.performanceclient.client.PerformanceClient;
import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;
import com.sim.member.memberdomain.dto.PerformanceDto;
import com.sim.member.memberdomain.error.ErrorMessage;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.error.MemberNotFoundException;
import com.sim.member.memberdomain.repository.MemberRepository;

import dto.Performance;
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
	private final PerformanceClient performanceClient;

	@Override
	public MemberDto findMemberById(Long memberId) {
		Member member = findById(memberId);
		return MemberDto.of(member);
	}

	@Override
	public MemberDto findMemberByUserId(String userId) {
		Assert.hasText(userId, "user id must exist");
		Member member = findByUserId(userId);
		return MemberDto.of(member);
	}

	@CircuitBreaker(name = "performanceApi", fallbackMethod = "fallback")
	@Override
	public MemberPerformanceDto selectPerformancesById(Long memberId) {
		Assert.notNull(memberId, "memberId must not be null");

		Member member = findById(memberId);
		List<Performance> performances = performanceClient.getPerformanceByMemberId(memberId);

		List<PerformanceDto> performanceDtos = getPerformanceDtos(performances);
		return MemberPerformanceDto.of(member, performanceDtos);
	}

	private List<PerformanceDto> getPerformanceDtos(List<Performance> performances) {
		return performances.stream().map(PerformanceDto::from).toList();
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
