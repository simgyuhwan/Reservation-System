package com.reservation.performances.converter;

import java.util.Objects;

import org.springframework.util.StringUtils;

import com.reservation.performances.domain.PerformanceType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * PerformanceTypeConverter.java
 * 공연 타입 Converter
 *
 * @author sgh
 * @since 2023.04.03
 */
@Converter
public class PerformanceTypeConverter implements AttributeConverter<PerformanceType, String> {

	@Override
	public String convertToDatabaseColumn(PerformanceType attribute) {
		if(Objects.isNull(attribute)) {
			return null;
		}
		return attribute.getType();
	}

	@Override
	public PerformanceType convertToEntityAttribute(String type) {
		if(!StringUtils.hasText(type)) {
			return null;
		}
		return PerformanceType.findByType(type);
	}
}
