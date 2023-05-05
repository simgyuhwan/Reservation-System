package com.reservation.memberservice.application;

import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.dto.response.MemberPerformanceDto;

/**
 * MemberQueryService.java
 * 회원 쿼리 관련 서비스
 *
 * @author sgh
 * @since 2023.03.23
 */
public interface MemberQueryService {
	MemberInfoDto findMemberByUserId(String userId);

	MemberPerformanceDto selectPerformancesById(Long memberId);
}
