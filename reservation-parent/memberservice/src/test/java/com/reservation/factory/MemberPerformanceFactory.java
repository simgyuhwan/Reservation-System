package com.reservation.factory;

import java.util.List;

import com.reservation.common.dto.PerformanceDto;
import com.reservation.memberservice.dto.response.MemberPerformanceDto;

/**
 * MemberPerformanceFactory.java
 * 회원 공연 정보 Factory 클래스
 *
 * @author sgh
 * @since 2023.05.04
 */
public class MemberPerformanceFactory {
    public static final String USER_ID = "test";
    public static final String USER_NAME = "홍길동";

   public static MemberPerformanceDto createMemberPerformanceDto() {
        return MemberPerformanceDto.of(USER_ID, USER_NAME, createPerformanceDtoList());
   }

   private static List<PerformanceDto> createPerformanceDtoList() {
       return PerformanceDtoFactory.createPerformanceDtoList();
   }
}
