package com.sim.member.memberdomain.domain;

import com.sim.member.memberdomain.dto.MemberCreateRequestDto;
import com.sim.member.memberdomain.dto.MemberUpdateDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
@EqualsAndHashCode
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

	public static Member create(MemberCreateRequestDto createDto) {
		return Member.builder()
			.userId(createDto.getUserId())
			.username(createDto.getUsername())
			.password(createDto.getPassword())
			.phoneNum(createDto.getPhoneNum())
			.address(createDto.getAddress())
			.build();
	}

	public void changeName(String username) {
		this.username = username;
	}

	public void updateInfo(MemberUpdateDto updateMemberDto) {
		this.username = updateMemberDto.getUsername();
		this.address = updateMemberDto.getAddress();
		this.phoneNum = updateMemberDto.getPhoneNum();
	}

}
