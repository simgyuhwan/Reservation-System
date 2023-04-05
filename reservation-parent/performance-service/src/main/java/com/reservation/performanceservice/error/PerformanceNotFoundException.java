package com.reservation.performanceservice.error;

/**
 * PerformanceNotFoundException.java
 * Performance Entity를 찾을 수 없을 때, 예외
 *
 * @author sgh
 * @since 2023.04.05
 */
public class PerformanceNotFoundException extends RuntimeException{

    public PerformanceNotFoundException(String message) {
        super(message);
    }
}
