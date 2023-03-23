package com.reservation.member.application;

import com.reservation.member.dto.request.SignUpDto;

public interface MemberCommandService {
	void signUp(final SignUpDto signUpDto);
}
