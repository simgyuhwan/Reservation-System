package com.sim.performance.common.util;

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
    private Object value;

    @Builder
    private QueryParameter(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    public static QueryParameter of(String key, Object value) {
        return QueryParameter.builder()
            .key(key)
            .value(value)
            .build();
    }
}
