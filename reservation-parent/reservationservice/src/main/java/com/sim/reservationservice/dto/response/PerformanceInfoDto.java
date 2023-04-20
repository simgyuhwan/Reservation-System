package com.sim.reservationservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PerformanceInfo.java
 * 공연 정보 DTO
 *
 * @author sgh
 * @since 2023.04.18
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceInfoDto {
    private String name;
    private String info;
    private String type;
    private String place;
    private boolean isAvailable;
    private String contactPhoneNum;
    private String contactPersonName;
    private Integer price;
    private List<PerformanceScheduleDto> schedules = new ArrayList<>();

    @Builder
    public PerformanceInfoDto(String name, String info, String type, String place, boolean isAvailable,
        String contactPhoneNum, Integer price, String contactPersonName, List<PerformanceScheduleDto> schedules) {
        this.name = name;
        this.info = info;
        this.type = type;
        this.place = place;
        this.isAvailable = isAvailable;
        this.contactPhoneNum = contactPhoneNum;
        this.price = price;
        this.contactPersonName = contactPersonName;
        this.schedules = schedules;
    }

}
