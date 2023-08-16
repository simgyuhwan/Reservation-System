package com.sim.member.memberdomain.service;

import com.sim.member.memberdomain.dto.MemberCreateRequestDto;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberUpdateDto;

public interface MemberCommandService {
	MemberDto signUp(final MemberCreateRequestDto signUpDto);

	MemberDto updateMemberInfo(String userId, MemberUpdateDto updateMemberDto);
}
