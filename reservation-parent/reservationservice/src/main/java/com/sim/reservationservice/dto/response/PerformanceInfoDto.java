package com.sim.reservationservice.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalTime> startTimes;
    private String place;
    private boolean isAvailable;
    private Integer audienceCount;
    private Integer availableSeats;
    private String contactPhoneNum;
    private String contactPersonName;
    private Integer price;

    @Builder
    public PerformanceInfoDto(String name, String info, String type, LocalDate startDate, LocalDate endDate,
        List<LocalTime> startTimes, String place, boolean isAvailable, Integer audienceCount,
        Integer availableSeats, String contactPhoneNum, Integer price, String contactPersonName) {
        this.name = name;
        this.info = info;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTimes = startTimes;
        this.place = place;
        this.isAvailable = isAvailable;
        this.audienceCount = audienceCount;
        this.availableSeats = availableSeats;
        this.contactPhoneNum = contactPhoneNum;
        this.price = price;
        this.contactPersonName = contactPersonName;
    }

}
