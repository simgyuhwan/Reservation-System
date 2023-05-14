package com.sim.member.memberdomain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sim.member.memberdomain.domain.Member;

/**
 * MemberRepository.java
 *
 * @author sgh
 * @since 2023.03.16
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);

	Optional<Member> findByPhoneNum(String phoneNum);

	Optional<Member> findByUserId(String userId);

	boolean existsByUserId(String userId);

}
