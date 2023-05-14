package com.sim.member.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sim.member.dto.request.MemberUpdateRequest;
import com.sim.member.dto.response.MemberInfoResponse;
import com.sim.member.dto.response.MemberPerformanceResponse;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;
import com.sim.member.memberdomain.service.MemberCommandService;
import com.sim.member.memberdomain.service.MemberQueryService;

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
	public MemberInfoResponse getMemberById(@PathVariable Long memberId) {
		MemberDto memberDto = memberQueryService.findMemberById(memberId);
		return MemberInfoResponse.from(memberDto);
	}

	@GetMapping
	@Timed(value = "members.search.userId")
	@Operation(summary = "[회원] 로그인 아이디로 회원 조회", description = "userId 회원 조회 API")
	public MemberInfoResponse getMember(@RequestParam("userId") String userId) {
		MemberDto memberDto = memberQueryService.findMemberByUserId(userId);
		return MemberInfoResponse.from(memberDto);
	}

	@PutMapping("/{userId}")
	@Timed(value = "members.update")
	@Operation(summary = "[회원] 회원 수정", description = "회원 수정 API")
	public MemberInfoResponse updateMemberInfo(@PathVariable String userId,
		@RequestBody @Validated MemberUpdateRequest updateRequest) {
		MemberDto memberDto = memberCommandService.updateMemberInfo(userId, updateRequest.toDto());
		return MemberInfoResponse.from(memberDto);
	}

	@GetMapping("/{memberId}/performances")
	@Timed(value = "members.search.performances")
	@Operation(summary = "[회원] 회원이 등록한 공연 조회", description = "회원 ID로 등록한 공연 조회")
	public MemberPerformanceResponse selectPerformancesById(@PathVariable Long memberId) {
		MemberPerformanceDto memberPerformanceDto = memberQueryService.selectPerformancesById(memberId);
		return MemberPerformanceResponse.from(memberPerformanceDto);
	}

}
