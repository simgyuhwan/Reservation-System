package com.reservation.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reservation.member.dao.MemberRepository;
import com.reservation.member.domain.Member;
import com.reservation.member.dto.response.MemberInfoDto;
import com.reservation.member.error.MemberNotFoundException;

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

	@Override
	public MemberInfoDto findMemberByUserId(String userId) {
		Assert.hasText(userId, "user id must exist");
		Member member = memberRepository.findByUserId(userId)
			.orElseThrow(() -> new MemberNotFoundException("Member with userId not found", userId));
		return MemberInfoDto.from(member);
	}
}
