package com.reservation.member.domain;

import com.reservation.member.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="user_id", unique = true, nullable = false)
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

    public static Member of(String userId, String username, String password, String phoneNum, String address) {
        return new Member(userId, username, password, phoneNum, address);
    }

    public void changeName(String username) {
        this.username = username;
    }
}
