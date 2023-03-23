package com.reservation.member.application;

import com.reservation.member.dto.SignUpRequest;

public interface MemberService {
	void signUp(final SignUpRequest signUpRequest);
}
