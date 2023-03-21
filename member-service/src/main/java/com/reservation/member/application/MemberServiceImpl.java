package com.reservation.member.application;

import com.reservation.member.application.mapper.SignUpRequestMapper;
import com.reservation.member.dao.MemberRepository;
import com.reservation.member.domain.Member;
import com.reservation.member.dto.SignUpRequest;
import com.reservation.member.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final SignUpRequestMapper mapper;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        Member member = mapper.toEntity(signUpRequest);
        validateMember(member);
        memberRepository.save(member);
    }

    private void validateMember(Member member) {
        if(isDuplicateUserId(member.getUserId())) {
            throw new DuplicateMemberException("중복된 아이디가 존재합니다.");
        }
    }

    private boolean isDuplicateUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }
}
