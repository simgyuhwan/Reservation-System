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
    private String id;
    private String message;

    private CreatedResponseDto(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public static CreatedResponseDto requestComplete(String id) {
        return new CreatedResponseDto(id, ResponseMessage.PERFORMANCE_CREATED_REQUEST_COMPLETE.getMessage());
    }
}
