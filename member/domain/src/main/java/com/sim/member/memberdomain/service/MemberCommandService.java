package com.sim.member.memberdomain.service;

import com.sim.member.memberdomain.dto.MemberCreateDto;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberUpdateDto;

public interface MemberCommandService {
	MemberDto signUp(final MemberCreateDto signUpDto);

	MemberDto updateMemberInfo(String userId, MemberUpdateDto updateMemberDto);
}
