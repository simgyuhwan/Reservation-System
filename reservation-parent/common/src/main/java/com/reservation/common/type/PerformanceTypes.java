package com.reservation.common.type;

import java.util.Arrays;

import lombok.Getter;

/**
 * PerformanceType.java
 * 공연 타입
 *
 * @author sgh
 * @since 2023.04.03
 */
@Getter
public enum PerformanceTypes {
    THEATER("THEATER", "극장"),
    CONCERT("CONCERT", "콘서트"),
    MUSICAL("MUSICAL", "뮤지컬"),
    OTHER( "OTHER","기타");

    private final String type;
    private final String name;

    PerformanceTypes(String type, String name) {
        this.name = name;
        this.type = type;
    }

    public static PerformanceTypes findByType(String type) {
        return Arrays.stream(PerformanceTypes.values()).toList()
            .stream()
            .filter(p -> p.type.equals(type))
            .findFirst()
            .orElse(PerformanceTypes.OTHER);
    }
}
