package com.reservation.memberservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.reservation.common.dto.PerformanceDto;

import lombok.AllArgsConstructor;
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
public class MemberPerformanceDto {
    private String userId;
    private String userName;
    private List<PerformanceDto> performances = new ArrayList<>();

    @Builder
    private MemberPerformanceDto(String userId, String userName,
        List<PerformanceDto> performances) {
        this.userId = userId;
        this.userName = userName;
        this.performances = performances;
    }

    public static MemberPerformanceDto of(String userId, String userName, List<PerformanceDto> performances) {
        return MemberPerformanceDto.builder()
            .userId(userId)
            .userName(userName)
            .performances(performances)
            .build();
    }

}
