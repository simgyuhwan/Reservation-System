package com.sim.reservationservice.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.core.annotations.QueryProjection;
import com.reservation.common.type.PerformanceType;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.domain.PerformanceSchedule;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "공연 이름", example = "오페라의 유령")
    private String name;
    @Schema(description = "공연 정보", example = "즐거운 공연 정보")
    private String info;
    @Schema(description = "공연 타입", example = "콘서트")
    private String type;
    @Schema(description = "공연 장소", example = "홍대 1 극장")
    private String place;
    @Schema(description = "예약 가능 여부", example = "true")
    private boolean isAvailable;
    @Schema(description = "담당자 번호", example = "010-xxxx-xxxx")
    private String contactPhoneNum;
    @Schema(description = "담당자 이름", example = "홍길동")
    private String contactPersonName;
    @Schema(description = "공연 가격", example = "15000")
    private Integer price;
    @Schema(description = "공연 시작 시간", example = "[15:00]")
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

    public static PerformanceInfoDto from(PerformanceInfo performanceInfo) {
        List<PerformanceScheduleDto> performanceScheduleDtos = performanceInfo.getPerformanceSchedules().stream()
            .map(PerformanceScheduleDto::from)
            .toList();

        return PerformanceInfoDto.builder()
            .name(performanceInfo.getName())
            .info(performanceInfo.getInfo())
            .type(performanceInfo.getType().getName())
            .place(performanceInfo.getPlace())
            .isAvailable(performanceInfo.isAvailable())
            .contactPhoneNum(performanceInfo.getContactPhoneNum())
            .contactPersonName(performanceInfo.getContactPersonName())
            .price(performanceInfo.getPrice())
            .schedules(performanceScheduleDtos)
            .build();
    }
}
