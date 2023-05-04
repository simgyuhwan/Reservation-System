package com.reservation.memberservice.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reservation.common.error.ErrorMessage;
import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.dto.response.MemberPerformanceDto;
import com.reservation.memberservice.error.InvalidUserIdException;
import com.reservation.memberservice.error.MemberNotFoundException;

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

	@Override
	public MemberInfoDto findMemberByUserId(String userId) {
		Assert.hasText(userId, "user id must exist");
		Member member = findByUserId(userId);
		return memberInfoDtoMapper.toDto(member);
	}

	@Override
	public MemberPerformanceDto selectPerformancesByUserId(String userId) {
		Assert.hasText(userId, "user id must exist");
		findMemberByUserId(userId);
		return null;
	}


	private Member findByUserId(String userId) {
		return memberRepository.findByUserId(userId)
			.orElseThrow(() -> new InvalidUserIdException(ErrorMessage.INVALID_USER_ID, userId));
	}
}
