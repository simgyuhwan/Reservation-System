package com.sim.member.memberdomain.service;

import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;

/**
 * MemberQueryService.java
 * 회원 쿼리 관련 서비스
 *
 * @author sgh
 * @since 2023.03.23
 */
public interface MemberQueryService {
	MemberDto findMemberById(Long memberId);

	MemberDto findMemberByUserId(String userId);

	MemberPerformanceDto selectPerformancesById(Long memberId);
}
