package com.reservation.performances.domain;

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
public enum PerformanceType {
    THEATER("THEATER", "극장"),
    CONCERT("CONCERT", "콘서트"),
    MUSICAL("MUSICAL", "뮤지컬"),
    OTHER( "OTHER","기타");

    private final String type;
    private final String name;

    PerformanceType(String type, String name) {
        this.name = name;
        this.type = type;
    }

    public static PerformanceType findByType(String type) {
        return Arrays.stream(PerformanceType.values()).toList()
            .stream()
            .filter(p -> p.type.equals(type))
            .findFirst()
            .orElse(PerformanceType.OTHER);
    }
}
