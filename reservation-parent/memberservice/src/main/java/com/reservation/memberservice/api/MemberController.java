package com.reservation.memberservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.memberservice.application.MemberCommandService;
import com.reservation.memberservice.application.MemberQueryService;
import com.reservation.memberservice.dto.request.UpdateMemberDto;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.dto.response.MemberPerformanceDto;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MemberController.java
 * 회원 컨트롤러
 *
 * @author sgh
 * @since 2023.03.17c
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
	private final MemberQueryService memberQueryService;
	private final MemberCommandService memberCommandService;

	@GetMapping("/{memberId}")
	@Timed(value = "members.search.id")
	@Operation(summary = "[회원] memberId로 회원 조회")
	public MemberInfoDto getMemberById(@PathVariable Long memberId) {
		return memberQueryService.findMemberById(memberId);
	}

	@GetMapping
	@Timed(value = "members.search.userId")
	@Operation(summary = "[회원] 로그인 아이디로 회원 조회", description = "userId 회원 조회 API")
	public MemberInfoDto getMember(@RequestParam("userId") String userId) {
		return memberQueryService.findMemberByUserId(userId);
	}

	@PutMapping("/{userId}")
	@Timed(value = "members.update")
	@Operation(summary = "[회원] 회원 수정", description = "회원 수정 API")
	public ResponseEntity<MemberInfoDto> updateMemberInfo(@PathVariable String userId,
		@RequestBody @Validated UpdateMemberDto updateMemberDto) {
		return ResponseEntity.ok(memberCommandService.updateMemberInfo(userId, updateMemberDto));
	}

	@GetMapping("/{memberId}/performances")
	@Timed(value = "members.search.performances")
	@Operation(summary = "[회원] 회원이 등록한 공연 조회", description = "회원 ID로 등록한 공연 조회")
	public MemberPerformanceDto selectPerformancesById(@PathVariable Long memberId) {
		return memberQueryService.selectPerformancesById(memberId);
	}

}
