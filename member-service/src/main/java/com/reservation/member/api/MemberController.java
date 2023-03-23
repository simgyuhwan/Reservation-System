package com.reservation.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.member.application.MemberQueryService;
import com.reservation.member.dto.response.MemberInfoDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MemberController.java
 * 회원 컨트롤러
 *
 * @author sgh
 * @since 2023.03.17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
	private final MemberQueryService memberQueryService;

	@GetMapping("/{userId}")
	@Operation(summary = "[회원] 회원 조회", description = "회원 조회 API")
	public ResponseEntity<MemberInfoDto> getMember(@PathVariable String userId) {
		return ResponseEntity.ok(memberQueryService.findMemberByUserId(userId));
	}
}
