package com.sim.performance.performancedomain.error;

/**
 * PerformanceNotFoundException.java
 * Performance Entity를 찾을 수 없을 때, 예외
 *
 * @author sgh
 * @since 2023.04.05
 */
public class PerformanceNotFoundException extends RuntimeException{
    private Long id;

    public PerformanceNotFoundException(ErrorMessage message, Long id) {
        super(message.name() + id);
        this.id = id;
    }
}
