package com.sim.member.memberdomain.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
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

	@Builder
	private Member(String userId, String username, String password, String phoneNum, String address) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phoneNum = phoneNum;
		this.address = address;
	}
	public static Member of(String userId, String username, String password, String phoneNum, String address) {
		return Member.builder()
			.userId(userId)
			.username(username)
			.password(password)
			.phoneNum(phoneNum)
			.address(address)
			.build();
	}

	public void changeName(String username) {
		Assert.hasText(username, "username must be included");
		this.username = username;
	}

	public void updateInfo(String username, String address, String phoneNum) {
		this.username = username;
		this.address = address;
		this.phoneNum = phoneNum;
	}

}
