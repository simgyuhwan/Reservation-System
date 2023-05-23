package com.sim.event.store.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/**
 * BaseEntity.java
 * Entity 기본 필드
 *
 * @author sgh
 * @since 2023.03.16
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	@CreatedDate
	@Column(name = "create_date")
	private LocalDateTime createDt;

	@LastModifiedDate
	@Column(name = "update_date")
	private LocalDateTime updateDt;

	@Column(name = "is_delete")
	private boolean isDelete = false;
}
