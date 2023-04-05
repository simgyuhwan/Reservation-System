package com.reservation.member.application;

import com.reservation.member.dto.request.SignUpDto;
import com.reservation.member.dto.request.UpdateMemberDto;
import com.reservation.member.dto.response.MemberInfoDto;

public interface MemberCommandService {
	void signUp(final SignUpDto signUpDto);

	MemberInfoDto updateMemberInfo(String userId, UpdateMemberDto updateMemberDto);
}
