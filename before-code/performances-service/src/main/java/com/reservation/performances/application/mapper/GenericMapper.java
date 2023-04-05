package com.reservation.performances.application.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * GenericMapper.java
 * 기본 Mapper 클래스
 *
 * @author sgh
 * @since 2023.03.17
 */
public interface GenericMapper<D, E> {
	D toDto(E entity);

	E toEntity(D dto);

	List<D> toDto(List<E> e);

	List<E> toEntity(List<D> d);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(D dto, @MappingTarget E entity);
}
