package com.reservation.common.util;

import lombok.Builder;
import lombok.Getter;

/**
 * QueryParameter.java
 * 쿼리 파라미터 클래스
 *
 * @author sgh
 * @since 2023.04.12
 */
@Getter
public class QueryParameter {
    private String key;
    private String value;

    @Builder
    private QueryParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    public QueryParameter of(String key, String value) {
        return QueryParameter.builder()
            .key(key)
            .value(value)
            .build();
    }
}
