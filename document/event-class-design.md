## 목차

#### [1. Event 설계](#1-event-설계-1)

##### [1) 추상 클래스](#1-추상-클래스-1)

##### [2) 인터페이스 상속](#2-인터페이스-상속-1)

##### [3) 메시지 전달](#3-메시지-전달-1)

#### [2. Event 생성](#2-event의-생성)

##### [1) 첫 번째 시도(Mapper)](#1-첫-번째-시도-mapper)

##### [2) 두 번째 시도(Factory method 패턴)](#2-두-번째-시도-factory-method-패턴)

##### [3) 세 번째 시도(Builder 패턴)](#3-세-번째-시도-builder-패턴)

##### [4) 네 번째 시도(Builder & Strategy 패턴)](#4-네-번째-시도-builder--strategy-패턴)

##### [5) 다섯 번째 시도(Builder 패턴)](#5-다섯-번째-시도-builder-패턴)

##### [6) 마지막 시도(나를 만드는건 나다)](#6-마지막-시도나를-만드는건-나다-1)

<br>

이번 글은 이벤트 클래스를 설계 및 구현하면서 겪었던 _*경험*_ 과 _*개인적인 생각*_ 들을 적으려고 한다. 기록을 위해 시간의 흐름대로 떠올랐던 생각들을 적었다.

# **1. Event 설계**

공통 Event를 설계하면서 중요하게 생각했던 것이 두 가지였는데. **필수** **필드**와 **설계의 목적 전달**이었다. 필수 필드는 **이벤트 생성 시간**, **이벤트 ID**, **이벤트 상태**, **소스(출처)**, **페이로드**이고 설계의 목적 전달로는 **Event 내부에 Payload가 포함된다는 구조**를 전달하고 싶었다

| 필드             | 필드명        |
| ---------------- | ------------- |
| 이벤트 ID        | eventId       |
| 이벤트 생성 시간 | eventDateTime |
| 이벤트 상태      | status        |
| 소스             | source        |
| 페이로드         | payload       |

<br>

## **1) 추상 클래스**

```java
// Event 추상 클래스
public abstract class Event<T extend Payload> {
	private final String id;
	private final LocalDateTime eventDateTime;
	private final String source;
	private final String status;
	private final T payload;
 ...
}

// Payload 인터페이스
public interface Payload{}
```

처음 생각했던 방식은 Event 클래스를 추상클래스로 만들고 Payload를 인터페이스로 만드는 방식이었다. 이로써 목적했던 모든 것들이 다 담겼다고 생각했다.

**원했던 필드들이 포함될 뿐더러 Payload가 Event 클래스 안에 있다는 것을 확인할 수 있었다** 그런데 실제 사용해보니 **예상치 못한** **불편함**을 느낄 수 있었다.

막상 상속 받아서 각 서비스에서 사용할 Event를 생성하려고 하니 ‘**추상 클래스에 있는 필드 값이 뭐였더라?’** 결국 추상 클래스를 들어가서 확인해야 했다. 그리고 추상 클래스 내부 필드를 임의대로 수정하고자 하니 추상 클래스에 메서드를 추가했어야 했다. 각 서비스의 요청와 필요에 따라 **추상 클래스의 수정은 불가피해 보였다**.

결국 다른 방법을 찾아보는데..

<br>

## **2) 인터페이스 상속**

```java
// Payload 인터페이스
public interface Payload{}

// Payload가 부모 클래스?
public inferface Event<T extend Payload> extend Payload {}

// 기본 구현 클래스
public DefaultEvent implements Event<Payload>{
	private ..eventDateTime;   // 구현 클래스에 직접 필드 지정
	...
	private T payload;
}
```

다음 방법은 **Event, Payload**를 **인터페이스**로 만들고 **Event**가 **Payload**를 **상속**하게 하는 방법이었다. 이제 Event를 구현할 때, 내부 필드로 payload 타입으로 가지고 있기만 하면 됐다. 다만 필수 필드는 구현 클래스에 따로 정의해야 했다.

된건가 싶었지만 처음에 목표로 했던 **두 가지는 단 하나도 담을 수 없었다**. 필드는 유연하게 원하는 값을 넣을 수 있지만 필수 필드를 강제할 순 없었다. 또 Event가 Payload 를 상속하면서 직관적으로 내가 원하는 구조가 아닌 그 반대의 구조가 떠올렸기 때문에 이번 역시 실패였다.

<br>

## **3) 메시지 전달**

```java
// Event 인터페이스
public interface Event {
	String getId();

	Payload getPayload();

	String getSource();

	String getStatus();

	LocalDateTime getEventDateTime();
}

// Payload 인터페이스
public interface Payload {}

// 기본 클래스
public class DefaultEvent<T extends Payload> implements Event{
	private String id;
	private LocalDateTime eventDateTime;
	private String status;
	private T payload;
	private String eventType;
	private String source;

	// override..getter..
}
```

**Spring Security**에서 아이디어를 얻었다. Spring Security의 **AuthenticationManager**나 **UserDetailService** 등을 보면 이 **인터페이스의 역할과 책임을 이름과 메서드(메시지)로 표현**했다. 이 아이디어를 착안해서 Event 인터페이스가 해야할 책임과 메시지를 메서드로 표현했다. Event를 구현한 클래스는 필수 값들을 뱉어내야 한다. 라는 메시지를 전달했다.

원했던 구조도 getPayload를 통해 나름 전달했다고 생각한다. 사실 더 좋은 방법이 안떠올랐다.. 최종적으로는 **DefaultEvent**를 만들어서 각 서비스에서는 Payload 인터페이스를 원하는 구조로 구현해서 교체하면서 사용할 수 있도록 만들었다.

<br>

---

<br>

# **2. Event의 생성**

이벤트 생성은 이벤트를 생성하면서 겪었던 시행착오들을 적었다. 알고 있던 디자인 패턴들을 사용해보고 가장 적절한 방법이라고 생각했던 것을 선택했던 사고의 흐름들을 적었다.

## **1) 첫 번째 시도 (Mapper)**

```java
// 공연 생성 메서드
public void createPerformance(PerformanceDto registrationDto) {
	validatePerformanceDate(registrationDto);
	Performance performance = performanceRepository.save(performanceDtoMapper.toEntity(registrationDto));
	eventPublisher.publishEvent(eventPublisher.publishEvent(createdEventMapper.toDto(performance)));
}
```

첫 시작은 **`createdEventMapper.toDto(performance)`** 이 코드부터였다.

공연 서비스에서 이벤트를 발행할 때, **Mapstruct**을 이용해서 이벤트 클래스로 매핑을 시켜주는 간단한 코드이다. 코드를 보며 들었던 생각은

- **모든 이벤트에 대한 mapper를 다 구현해야 되는거 아닌가**?
- **모든 이벤트는 공통된 구조를 가지고 payload만 바뀔텐데 mapper가 필요할까?**

였다. 그래서 생성에 대한 책임은 각 객체에게 넘겨보자는 생각이 났다. 다음과 같이 바꿨다.

<br>

## **2) 두 번째 시도 (Factory method 패턴)**

<br>

**사용 코드**

```java
private PerformanceEvent createPerformanceEvent(Performance performance) {
	PerformanceCreatedEventPayload payload = PerformanceCreatedEventPayload.from(performance); // Payload 생성
	return PerformanceEvent.from(EventType.PERFORMANCE_CREATED, payload); // Event 생성
}
```

Event 클래스와 Payload 클래스에게 **팩토리 메서드 패턴**을(from)을 적용했다.

이 소스를 보며 세 가지 문제점이 떠올랐는데.

- **Event 클래스를 생성할 때, 내부적으로 payload 까지 같이 만드는게 낫지 않을까?**
- **Event, Payload 구현체를 다 알아야 하니 관리하기 힘들지 않을까?**
- **Event의 공통 부분을 제외하고 Payload 값만 바뀌니 그 부분만 분리할까?**

<br>

## **3) 세 번째 시도 (Builder 패턴)**

<br>

**사용 코드**

```java
private PerformanceEvent<Payload> createPerformanceEvent(Performance performance) {
	return PerformanceEventBuilder.withEventType(EventType.PERFORMANCE_CREATED)
		.withPayload(PerformancePayloadFactory.createdEvent(performance))
		.create();
}

```

**빌더, PerformanceEvent 클래스**

```java
// 빌더 클래스
public class PerformanceEventBuilder<T extends Payload> {
	private EventType eventType;
	private T payload;

	public static<T> Builder<T> withEventType(EventType eventType) {
	    return new Builder<>(eventType);
	}

	public static class Builder<T> {
	    private EventType eventType;
	    private T payload;

	    public Builder(EventType eventType) {
	        this.eventType = eventType;
	    }

	    public Builder<T> withPayload(Payload payload) {
	        this.payload = payload;
	        return this;
	    }

	    public PerformanceEvent create() {
	        return PerformanceEvent.from(eventType, payload);
	    }
	}
}

// PerformanceEvent 클래스
public class PerformanceEvent implements Event {
 ....

	public static PerformanceEvent from PerformanceEvent(EventType eventType, Payload payload) {
		switch(eventType) {
			case PERFORMANCE_CREATED_EVENT : ...;
			...
			case ? : ...;
			....
		}
	}
```

생성에 대한 것을 **빌더 패턴**을 적용해서 만들었다. 다시보니 아주 어중간한 빌더 패턴이다. 빌더 패턴을 사용하는 이유는

- **매개변수가 많은 생성자가 필요할 경우**
- **생성자의 매개변수 순서가 바뀌면 안되는 경우**
- **불변 객체를 만들려는 경우**

쉽게 말해 꼭 필요한 매개변수를 원하는 순서로 넣어 완전한 객체로 만들기 위해서 사용하는데. 위의 코드는 순서도 보장 못하며 매개변수가 많지도 않다. 그리고 각 생성의 책임이 Event의 생성은 PerformanceEvent가 Payload 생성은 PerformancePayloadFactory가 하며, Event, Payload 생성은 PerformanceEventBuilder이 한다. 생성 책임이 세 곳으로 분리됐다.

1. **객체 생성 순서를 보장 못하기 때문에 payload 없이도 이벤트 생성이 가능**
2. **이벤트가 증가하면 switch문의 case 증가**
3. **빌더 클래스를 쓰는 의미가 사라짐**

<br>

## **4) 네 번째 시도 (Builder & Strategy 패턴)**

<br>

**사용 코드**

```java
private PerformanceEvent<Payload> createPerformanceEvent(Performance performance) {
	return PerformanceEventBuilder.withPayload(PerformancePayloadFactory.createdEvent(performance))
		.withEventCreator(EventCreatorFactor.getCreatedEventCreator())
		.create();
}
```

**빌더 클래스**

```java

// 빌더 클래스
public class PerformanceEventBuilder {

	public static Builder withPayload(Payload payload) {
	    return new Builder(payload);
	}

	public static class Builder {
		private Payload payload;
		private EventCreator eventCreator;

	    public Builder(Payload payload) {
	        this.eventType = eventType;
	    }

	    public Builder withEventCreator(EventCreator eventCreator) {
	        this.eventCreator = eventCreator;
	        return this;
	    }

	    public PerformanceEvent create() {
	        return payloadCreator.createEvent(payload);
	    }
	}
}

// 이벤트 생성 인터페이스
public interface EventCreator {
	Event createEvent(Payload payload);
}
```

switch를 어떻게 분리할까? 라는 생각에 빠져 전략 패턴을 사용했었다.

**PayloadCreator**를 인터페이스를 활용하여 switch 부분을 클래스 단위로 확장시켰다. 이쯤되니 빌더 패턴 왜 필요한가 싶었다

- **빌더 패턴이 맞나?**
- **Payload, EventCreator 구현 클래스 관리 포인트 증가**

하지만 막상 구현해 보니 이런 간단한 구조가 아니라 좀 더 복잡한 구조의 경우, 유용하게 쓰일 것 같았다. 괜히 디자인 패턴이 아닌가 보다. 역시 직접 써봐야 아는 듯 싶다.

<br>

## **5) 다섯 번째 시도 (Builder 패턴)**

<br>

**사용 코드**

```java
private PerformanceEvent createPerformanceEvent(Performance performance) {
	return PerformanceEventBuilder.pending(EventType.PERFORMANCE_CREATED)
		.payload(() -> PerformanceCreatedPayload.from(performance))
		.create();
}

```

**빌더 코드**

```java

// 빌더 패턴
public class PerformanceEventBuilder {
	private String id;
	private EventType eventType;
	private SourceType sourceType;
	private EventStatusType statusType;
	private Payload payload;
	private LocalDateTime eventDateTime;

	private PerformanceEventBuilder() {
	}

	// pending 상태 표현
	public static DefaultBuilder pending(EventType eventType) {
		return new DefaultBuilder()
			.id(UUID.randomUUID().toString())
			.eventDateTime(LocalDateTime.now())
			.eventStatusType(EventStatusType.PENDING)
			.sourceType(SourceType.PERFORMANCE_SERVICE)
			.eventType(eventType);
	}

  	// 기본 빌더
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

	// payload 생성 빌더
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
```

다섯 번째 시도는 **springframework.http.ResponseEntity** **클래스**에서 아이디어를 착안했다. 이 클래스는 **HttpEntity** 클래스를 확장해서 **HTTP** 상태 코드를 추가한 클래스이다.

사용자는 `ResponseEntity.ok()` , `ResponseEntity.status()` 와 같이 먼저 상태 값의 정의를 해야 한다. 물론 동시에 body를 매개변수로 넣어줄 수 있다.

이렇게 상태와 바디 값을 강제하였고 그 다음으로는 **`body()`** , **`header()`** 혹은 **`build()`** 를 통해서 바로 객체를 생성할 수 있다.

빌더 클래스를 내부에 넣음으로써 코드의 내부의 복잡도는 수직 상승했지만 사용했을 때의 편의성이 증가했고 더 직관적이었다.

이벤트의 상태는 한정적이니 그 중 하나인 **`pending()`** 을 만들었다.

`pending()` 을 사용하면 자동으로 이벤트 상태는 PENDING 값으로 넣어준다. 동시에 필요한 나머지 필드도 넣어준다.

지금은 pending 하나만 추가했지만 상황에 따라 **retry, ready, success, fail** 등 원하는 상태를 표현할 수 있다. 또 함수형 인터페이스 **Supplier**를 사용해서 외부에서는 Payload를 만들어서 전달하면 된다.

여태까지의 시도 중에는 가장 마음에 들었다. 이게 빌더 패턴이구나 느껴졌으며 `pending() → payload() → create()`

사용 순서를 보장해준다.

물론 이 방법도 완전하지는 않다. **PerformanceEvent 클래스와 Builder 클래스를 모두 관리**하기 때문이다.

<br>

## 6) 마지막 시도(나를 만드는건 나다)

<br>

**사용 코드**

```java
private PerformanceEvent createPerformanceEvent(Performance performance) {
	return PerformanceEvent.pending(EventType.PERFORMANCE_CREATED)
		.payload(() -> PerformanceCreatedPayload.from(performance));
}

```

**PerformanceEvent 클래스**

```java

// PerformanceEvent 클래스
public class PerformanceEvent implements Event {
	private final String id;
	private final LocalDateTime eventDateTime;
	private final EventStatusType status;
	private final SourceType source;
	private final Payload payload;
	private final EventType eventType;

	//..getter

	private PerformanceEvent(Builder builder) {
		this.id = builder.id;
		this.eventDateTime = builder.eventDateTime;
		this.status = builder.status;
		this.payload = builder.payload;
		this.eventType = builder.eventType;
		this.source = builder.source;
	}

	public EventType getEventType() {
		return eventType;
	}

	public static Builder pending(EventType eventType) {
		return new Builder()
			.id(UUID.randomUUID().toString())
			.eventDateTime(LocalDateTime.now())
			.source(SourceType.PERFORMANCE_SERVICE)
			.eventType(eventType)
			.status(EventStatusType.PENDING);
	}

	public static class Builder {
		private String id;
		private LocalDateTime eventDateTime;
		private EventStatusType status;
		private SourceType source;
		private Payload payload;
		private EventType eventType;

		private Builder(){}

		private Builder id(String id) {
			this.id = id;
			return this;
		}

		private Builder eventDateTime(LocalDateTime eventDateTime) {
			this.eventDateTime = eventDateTime;
			return this;
		}

		private Builder status(EventStatusType status) {
			this.status = status;
			return this;
		}

		private Builder eventType(EventType eventType) {
			this.eventType = eventType;
			return this;
		}

		private Builder source(SourceType source) {
			this.source = source;
			return this;
		}

		public PerformanceEvent payload(Supplier<Payload> supplier) {
			this.payload = supplier.get();
			return new PerformanceEvent(this);
		}
	}
}
```

객체의 생성에 대한 책임을 **PerformanceEvent** 자체에 넘겨버렸다. 그리고 다섯 번째 방법을 가져와 내부적으로 빌더 클래스를 넣어놨다.

공연 이벤트에 대한 생성과 비즈니스 로직은 모두 이 내부에 넘겨서 객체가 일을 할 수 있도록 바꿔버렸다. 객체의 생성과 비즈니스 로직이 한 곳에 집중된 만큼 객체가 커질 수 있고 복잡해질 수 있다고 생각된다.

하지만 지금 정도의 프로젝트에선 무리없다고 생각되며 관리 포인트도 한 곳으로 집중되었다. 계속 사용하다보면 더 좋은게 떠오를지 모르겠지만 지금은 이정도선에서 만족했다.

각각의 시도를 통해서 각자의 장단점이 느껴졌다. 글로 읽을 때와 직접 구현해보고 적용해봤을 때의 차이는 크다는 것을 실감했다.

프로젝트 구조나 범용성, 유연성, 복잡도에 따라 맞는 방법을 있을 것이다. 개발은 모든게 트레이드오프라는 말이 조금 와닿았던 경험이었던 것 같다.
