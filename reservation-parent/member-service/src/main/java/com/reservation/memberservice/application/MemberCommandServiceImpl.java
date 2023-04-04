package com.reservation.memberservice.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.reservation.memberservice.application.mapper.MemberInfoDtoMapper;
import com.reservation.memberservice.application.mapper.SignUpRequestMapper;
import com.reservation.memberservice.dao.MemberRepository;
import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.request.SignUpDto;
import com.reservation.memberservice.dto.request.UpdateMemberDto;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.error.DuplicateMemberException;
import com.reservation.memberservice.error.MemberNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {
	private final MemberRepository memberRepository;
	private final SignUpRequestMapper signUpRequestMapper;
	private final MemberInfoDtoMapper memberInfoDtoMapper;

	@Override
	public void signUp(SignUpDto signUpDto) {
		Member member = signUpRequestMapper.toEntity(signUpDto);
		validateMember(member);
		memberRepository.save(member);
	}

	@Override
	@Transactional
	public MemberInfoDto updateMemberInfo(final String userId, UpdateMemberDto updateMemberDto) {
		validateUserId(userId);
		Member member = findMemberByUserId(userId);
		member.updateInfo(updateMemberDto);
		return memberInfoDtoMapper.toDto(member);
	}

	private void validateUserId(String userId) {
		Assert.hasText(userId, "user id must exist");
	}

	private Member findMemberByUserId(String userId) {
		return memberRepository.findByUserId(userId)
			.orElseThrow(() -> new MemberNotFoundException("Member with userId not found", userId));
	}

	private void validateMember(Member member) {
		if (isDuplicateUserId(member.getUserId())) {
			throw new DuplicateMemberException("중복된 아이디가 존재합니다.");
		}
	}

	private boolean isDuplicateUserId(String userId) {
		return memberRepository.existsByUserId(userId);
	}

}
