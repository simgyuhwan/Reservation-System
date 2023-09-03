package com.sim.member.memberdomain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberCreateRequestDto;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberUpdateDto;
import com.sim.member.memberdomain.error.DuplicateMemberException;
import com.sim.member.memberdomain.error.ErrorMessage;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {
	private final MemberRepository memberRepository;

	@Override
	public MemberDto signUp(MemberCreateRequestDto memberCreateRequestDto) {
		Member member = memberCreateRequestDto.toEntity();
		validateMember(member);
		saveMember(member);
		return MemberDto.of(member);
	}

	@Override
	public MemberDto updateMemberInfo(final String userId, MemberUpdateDto updateMemberDto) {
		Assert.hasText(userId, "user id must exist");

		Member member = memberRepository.findByUserId(userId)
			.orElseThrow(() -> new InvalidUserIdException(ErrorMessage.INVALID_USER_ID, userId));
		member.updateInfo(updateMemberDto.getUsername(), updateMemberDto.getAddress(), updateMemberDto.getPhoneNum());
		return MemberDto.of(member);
	}

	private void validateMember(Member member) {
		if (isDuplicateUserId(member.getUserId())) {
			throw new DuplicateMemberException(ErrorMessage.DUPLICATE_MEMBER, member.getUserId());
		}
	}

	private boolean isDuplicateUserId(String userId) {
		return memberRepository.existsByUserId(userId);
	}

	private void saveMember(Member member) {
		memberRepository.save(member);
	}

}
