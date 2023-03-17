package com.reservation.member.dao;

import com.reservation.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * MemberRepository.java
 *
 * @author sgh
 * @since 2023.03.16
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByPhoneNum(String phoneNum);

    boolean existsByUserId(String userId);
}
