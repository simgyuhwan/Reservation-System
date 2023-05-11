package com.reservation.performanceservice.dto.response;

import com.reservation.common.types.ResponseMessage;

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
public class CreatedResponseDto {
    private Long performanceId;
    private String message;

    private CreatedResponseDto(Long performanceId, String message) {
        this.performanceId = performanceId;
        this.message = message;
    }

    public static CreatedResponseDto requestComplete(Long performanceId) {
        return new CreatedResponseDto(performanceId, ResponseMessage.PERFORMANCE_CREATED_REQUEST_COMPLETE.getMessage());
    }
}
