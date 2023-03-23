package com.reservation.member.domain;

import com.reservation.member.dto.SignUpRequest;
import com.reservation.member.global.model.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

/**
 * Member.java
 * 회원 클래스
 *
 * @author sgh
 * @since 2023.03.16
 */

@Getter
@Table(name = "member")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", unique = true, nullable = false)
	private String userId;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNum;

	@Column(nullable = false)
	private String address;

	private Member(String userId, String username, String password, String phoneNum, String address) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phoneNum = phoneNum;
		this.address = address;
	}

	public static Member of(final String userId, final String username,
		final String password, final String phoneNum, final String address) {
		validateArguments(userId, username, password, phoneNum, address);
		return new Member(userId, username, password, phoneNum, address);
	}

	public static Member from(final SignUpRequest request) {
		validateArguments(request.getUserId(), request.getUsername(), request.getPassword(), request.getPhoneNum(),
			request.getAddress());
		return new Member(request.getUserId(), request.getUsername(), request.getPassword(), request.getPhoneNum(),
			request.getAddress());
	}

	private static void validateArguments(String userId, String username, String password, String phoneNum,
		String address) {
		Assert.hasText(userId, "회원 아이디는 반드시 필요합니다.");
		Assert.hasText(username, "회원 이름은 반드시 필요합니다.");
		Assert.hasText(password, "비밀번호는 반드시 필요합니다.");
		Assert.hasText(phoneNum, "핸드폰 번호는 반드시 필요합니다.");
		Assert.hasText(address, "주소는 반드시 필요합니다.");
	}

	public void changeName(String username) {
		this.username = username;
	}
}
