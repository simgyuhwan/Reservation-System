package com.reservation.performanceservice.event;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

import com.reservation.common.event.payload.Payload;
import com.reservation.common.types.EventStatusType;
import com.reservation.common.types.SourceType;
import com.reservation.performanceservice.types.EventType;

import lombok.Getter;

/**
 * PerformanceEventBuilder.java
 * 공연 생성 이벤트 빌더
 *
 * @author sgh
 * @since 2023.05.08
 */
@Getter
public class PerformanceEventBuilder {
	private String id;
	private EventType eventType;
	private SourceType sourceType;
	private EventStatusType statusType;
	private Payload payload;
	private LocalDateTime eventDateTime;

	private PerformanceEventBuilder() {
	}

	public static DefaultBuilder pending(EventType eventType) {
		return new DefaultBuilder()
			.id(UUID.randomUUID().toString())
			.eventDateTime(LocalDateTime.now())
			.eventStatusType(EventStatusType.PENDING)
			.sourceType(SourceType.PERFORMANCE_SERVICE)
			.eventType(eventType);
	}

	public static class DefaultBuilder {
		private final PerformanceEventBuilder builder = new PerformanceEventBuilder();

		private DefaultBuilder() {
		}

		private DefaultBuilder id(String id) {
			builder.id = id;
			return this;
		}

		private DefaultBuilder eventType(EventType eventType) {
			builder.eventType = eventType;
			return this;
		}

		private DefaultBuilder sourceType(SourceType sourceType) {
			builder.sourceType = sourceType;
			return this;
		}

		private DefaultBuilder eventStatusType(EventStatusType statusType) {
			builder.statusType = statusType;
			return this;
		}

		private DefaultBuilder eventDateTime(LocalDateTime eventDateTime) {
			builder.eventDateTime = eventDateTime;
			return this;
		}

		public PayloadBuilder payload(Supplier<Payload> supplier) {
			builder.payload = supplier.get();
			return new PayloadBuilder(builder);
		}
	}

	public static class PayloadBuilder {
		private PerformanceEventBuilder builder;

		private PayloadBuilder(PerformanceEventBuilder builder) {
			this.builder = builder;
		}

		public PerformanceEvent create() {
			return PerformanceEvent.builder()
				.id(builder.id)
				.eventDateTime(builder.eventDateTime)
				.status(builder.statusType)
				.source(builder.sourceType)
				.payload(builder.payload)
				.eventType(builder.eventType)
				.build();
		}
	}
}
