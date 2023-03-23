package com.reservation.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reservation.member.application.mapper.SignUpRequestMapper;
import com.reservation.member.dao.MemberRepository;
import com.reservation.member.domain.Member;
import com.reservation.member.dto.request.SignUpDto;
import com.reservation.member.error.DuplicateMemberException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {
	private final MemberRepository memberRepository;
	private final SignUpRequestMapper mapper;

	@Override
	public void signUp(SignUpDto signUpDto) {
		Member member = mapper.toEntity(signUpDto);
		validateMember(member);
		memberRepository.save(member);
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
