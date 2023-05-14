package com.reservation.memberservice.application;

import com.reservation.memberservice.dto.request.SignUpDto;
import com.reservation.memberservice.dto.request.UpdateMemberDto;
import com.reservation.memberservice.dto.response.MemberInfoDto;

public interface MemberCommandService {
	void signUp(final SignUpDto signUpDto);

	MemberInfoDto updateMemberInfo(String userId, UpdateMemberDto updateMemberDto);
}
