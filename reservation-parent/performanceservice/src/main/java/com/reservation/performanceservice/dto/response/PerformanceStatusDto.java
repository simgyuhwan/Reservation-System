package com.reservation.performanceservice.dto.response;

import com.reservation.common.types.ResponseMessage;
import com.reservation.performanceservice.domain.Performance;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * CreatedResponseDto.java
 * 공연 생성에 대한 응답 DTO
 *
 * @author sgh
 * @since 2023.05.10
 */
@Getter
@NoArgsConstructor
public class PerformanceStatusDto {
    private Long performanceId;
    private String message;

    private PerformanceStatusDto(Long performanceId, String message) {
        this.performanceId = performanceId;
        this.message = message;
    }

    public static PerformanceStatusDto requestComplete(Long performanceId) {
        return new PerformanceStatusDto(performanceId, ResponseMessage.PERFORMANCE_CREATED_REQUEST_COMPLETE.getMessage());
    }

    public static PerformanceStatusDto from(Performance performance) {
        String message = performance.getRegistrationStatus().getMessage();
        Long performanceId = performance.getId();
        return new PerformanceStatusDto(performanceId, message);
    }
}
