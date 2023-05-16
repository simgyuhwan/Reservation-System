package com.sim.reservation.boot.factory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;

/**
 * PerformanceInfoDtoFactory.java
 * PerformanceInfoDtoFactory 생성 클래스
 *
 * @author sgh
 * @since 2023.05.15
 */
public class PerformanceInfoDtoFactory {
    public static final String NAME = "바람과 함께 사라지다";
    public static final String INFO = "공연 소개";
    public static final String TYPE = "MUSICAL";
    public static final String PLACE = "홍대 1극장";
    public static final boolean IS_AVAILABLE = true;
    public static final int PRICE = 15000;
    public static final String CONTACT_PHONE_NUM = "010-1234-4569";
    public static final String CONTACT_PERSON_NAME = "홍길동";

    public static Page<PerformanceInfoDto> createPerformanceInfoDtoListForPage() {
        List<PerformanceInfoDto> performanceInfoDtoList = List.of(createPerformanceInfoDto());
        PageRequest pageable = PageRequest.of(1, 15);
        return new PageImpl<>(performanceInfoDtoList, pageable, performanceInfoDtoList.size());
    }

    public static PerformanceInfoDto createPerformanceInfoDto() {
        return PerformanceInfoDto.builder()
            .name(NAME)
            .info(INFO)
            .type(TYPE)
            .place(PLACE)
            .isAvailable(IS_AVAILABLE)
            .price(PRICE)
            .contactPhoneNum(CONTACT_PHONE_NUM)
            .contactPersonName(CONTACT_PERSON_NAME)
            .build();
    }

    public static PerformanceInfoDto createPerformanceInfoDto(String name, String info, String type, String place, boolean isAvailable, int price, String contactPhoneNum, String contactPersonName) {
        return PerformanceInfoDto.builder()
            .name(name)
            .info(info)
            .type(type)
            .place(place)
            .isAvailable(isAvailable)
            .price(price)
            .contactPhoneNum(contactPhoneNum)
            .contactPersonName(contactPersonName)
            .build();
    }
}
