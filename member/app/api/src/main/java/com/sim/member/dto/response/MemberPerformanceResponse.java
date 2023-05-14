package com.sim.member.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;
import com.sim.member.memberdomain.dto.PerformanceDto;

import dto.Performance;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MemberPerformanceDto.java
 * 회원이 등록한 공연 정보가 담긴 DTO
 *
 * @author sgh
 * @since 2023.05.04
 */
@Getter
@NoArgsConstructor
public class MemberPerformanceResponse {
    private String userId;
    private String userName;
    private List<PerformanceDto> performances = new ArrayList<>();

    @Builder
    private MemberPerformanceResponse(String userId, String userName,
        List<PerformanceDto> performances) {
        this.userId = userId;
        this.userName = userName;
        this.performances = performances;
    }

    public static MemberPerformanceResponse from(MemberPerformanceDto memberPerformanceDto) {
        return MemberPerformanceResponse.builder()
            .userId(memberPerformanceDto.getUserId())
            .userName(memberPerformanceDto.getUserName())
            .performances(memberPerformanceDto.getPerformances())
            .build();
    }

    public static MemberPerformanceResponse of(Member member, List<PerformanceDto> performances) {
        return MemberPerformanceResponse.builder()
            .userId(member.getUserId())
            .userName(member.getUsername())
            .performances(performances)
            .build();
    }

}
