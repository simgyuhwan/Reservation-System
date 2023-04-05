package com.reservation.member.application;

import com.reservation.member.dto.response.MemberInfoDto;

/**
 * MemberQueryService.java
 * 회원 쿼리 관련 서비스
 *
 * @author sgh
 * @since 2023.03.23
 */
public interface MemberQueryService {
	MemberInfoDto findMemberByUserId(String userId);
}
