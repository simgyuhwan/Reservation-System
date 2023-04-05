package com.reservation.performanceservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.reservation.performanceservice.domain.PerformanceTimeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * ValidPerformanceTime.java
 * 공연시간 검증 애너테이션
 *
 * @author sgh
 * @since 2023.04.04
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PerformanceTimeValidator.class)
public @interface ValidPerformanceTime {
    String message() default "공연 시간 형식이 잘못되었습니다. ex) '15:45'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
