package com.reservation.common.type;

import java.util.Objects;

import org.springframework.util.StringUtils;

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
public class PerformanceTypeConverter implements AttributeConverter<PerformanceTypes, String> {

	@Override
	public String convertToDatabaseColumn(PerformanceTypes attribute) {
		if(Objects.isNull(attribute)) {
			return null;
		}
		return attribute.getType();
	}

	@Override
	public PerformanceTypes convertToEntityAttribute(String type) {
		if(!StringUtils.hasText(type)) {
			return null;
		}
		return PerformanceTypes.findByType(type);
	}
}
