package com.sim.performance.validator;

import java.util.Set;
import java.util.regex.Pattern;

import com.sim.performance.annotation.ValidPerformanceTimes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * PerformanceTimeValidator.java
 * 공연 시간 용 검증 클래스
 *
 * @author sgh
 * @since 2023.04.04
 */
public class PerformanceTimeValidator implements ConstraintValidator<ValidPerformanceTimes, Set<String>> {
	private static final Pattern PATTERN = Pattern.compile("^(?:[01][0-9]|2[0-4]):[0-5][0-9]$");

	@Override
	public void initialize(ValidPerformanceTimes constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Set<String> times, ConstraintValidatorContext context) {
		if(times == null || times.isEmpty()) {
			return false;
		}
		for (String time : times) {
			if(!PATTERN.matcher(time).matches()) {
				return false;
			}
		}
		return true;
	}
}
