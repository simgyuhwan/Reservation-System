package com.reservation.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.member.application.MemberCreator;
import com.reservation.member.dto.SignUpRequest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SignUpController.java
 * 회원가입 컨트롤러
 *
 * @author sgh
 * @since 2023.03.21
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {
	private final MemberCreator memberCreator;

	@PostMapping("/signup")
	@Operation(summary = "[회원] 회원 가입", description = "회원 가입 API")
	public ResponseEntity<Void> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
		memberCreator.create(signUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
