## 목차

#### [1. Event 설계](#1-event-설계-1)

##### [1) 추상 클래스](#1-추상-클래스-1)

##### [2) 인터페이스 상속](#2-인터페이스-상속-1)

##### [3) 메시지 전달](#3-메시지-전달-1)

#### [2. Event 생성](#2-event-생성-1)

##### [첫 번째 시도(Mapper)](#첫-번째-시도-mapper)

##### [두 번째 시도(Factory method 패턴)](#네-번째-시도-builder--strategy-패턴)

##### [세 번째 시도(Builder 패턴)](#세-번째-시도-builder-패턴)

##### [네 번째 시도(Builder & Strategy 패턴)](#네-번째-시도-builder--strategy-패턴)

##### [마지막 시도(Builder 패턴)](#마지막-시도-builder-패턴)

<br>

이번 글은 이벤트 클래스를 설계 및 구현하면서 겪었던 _경험_ 과 _개인적인 생각_ 들을 적으려고 한다. 장황하고 길 수 있으나 시간의 흐름대로 떠올랐던 생각들을 적어보려고 한다.

## **1. Event 설계**

공통 Event를 설계하면서 중요하게 생각했던 것이 두 가지였는데. **필드**와 **설계의 목적 전달**이었다. 필드는 **이벤트 생성 시간**, **이벤트 ID**, **이벤트 상태**, **소스(출처)**, **페이로드**이고 설계의 목적 전달로는 **Event 내부에 Payload가 포함된다는 구조**를 전달하고 싶었다

<br>

| 필드             | 필드명        |
| ---------------- | ------------- |
| 이벤트 ID        | eventId       |
| 이벤트 생성 시간 | eventDateTime |
| 이벤트 상태      | status        |
| 소스             | source        |
| 페이로드         | payload       |

<br>

### **1) 추상 클래스**

```java
public abstract class Event<T extend Payload> {
	private final String id;
	private final LocalDateTime eventDateTime;
	private final String source;
	private final String status;
	private final T payload;
 ...
}

public interface Payload{}
```

처음 생각했던 방식은 Event 클래스를 추상클래스로 만들고 Payload를 인터페이스로 만드는 방식이었다. 목적했던 모든 것들이 다 담겨있었다.

**원했던 필드들이 포함될 뿐더러 Payload가 Event 클래스 안에 있다는 것을 확인할 수 있었다.** 그런데 실제 사용해보니 **예상치 못한** **불편함**을 느낄 수 있었다.

막상 상속 받아서 각 서비스에서 사용할 Event를 생성하려고 하니 ‘**추상 클래스에 있는 필드 값이 뭐였더라?’** 결국 추상 클래스를 들어가서 확인해야 했다. 그리고 객체로 만들고 추상 클래스 내부 필드를 임의대로 수정하고자 하니 추상 클래스에 메서드를 추가했어야 했다.

만약 **common** 모듈에 함께 쓸 이벤트 구현 클래스를 만들었다면 각 서비스의 요청과 필요에 따라 **추상 클래스의 수정은 불가피해 보였다**.

결국 다른 방법을 찾아보는데..

<br>

### **2) 인터페이스 상속**

```java
public interface Payload{}

public inferface Event<T extend Payload> extend Payload {}

// 구현 클래스
public DefaultEvent implements Event<Payload>{
	..eventDateTime
	...
	T payload;
}
```

다음 방법은 **Event, Payload를 인터페이스로 만들고 Event가 Payload를 상속**하게 하는 방법이었다. 이제 Event를 구현할 때, 내부 필드로 payload 타입으로 가지고 있기만 하면 됐다. 다만 필요한 필드는 구현 클래스에 따로 정의해야 했다.

된건가 싶었지만 처음에 목표로 했던 **두 가지는 단 하나도 담을 수 없었다**. 필드는 유연하게 원하는 값을 넣을 수 있지만 꼭 넣어야 하는 필드를 강제할 순 없었다. 또 Event가 Payload 를 상속하면서 직관적으로 내가 원하는 구조가 아닌 그 반대의 구조가 떠올렸기 때문에 이번 역시 실패였다.

<br>

### **3) 메시지 전달**

```java
public interface Event {
	String getId();

	Payload getPayload();

	SourceType getSource();

	EventStatusType getStatus();

	LocalDateTime getEventDateTime();
}

public interface Payload {}

// 기본 클래스
public class DefaultEvent<T extends Payload> implements Event{
	private String id;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime eventDateTime;
	private String status;
	private T payload;
	private String eventType;
	private String source;

	// override..getter..
}
```

**Spring Security**에서 아이디어를 얻었다. Spring Security의 **AuthenticationManager**나 **UserDetailService** 등을 보면 이 **인터페이스의 역할과 책임을 이름과 메서드(메시지)로 표현**했다. 이 아이디어를 착안해서 Event 인터페이스가 해야할 책임과 메시지를 메서드로 표현함으로써 이를 구현하는 구현 클래스는 이 메서드를 통해 강제적으로 구현해야 했다.

덕분에 필요한 필드에 대한 강조가 되었다. 구조는 getPayload를 통해 알렸다고 생각한다. 사실 더 좋은 방법이 안떠올랐다. 최종적으로는 **DefaultEvent**를 만든 뒤, 각 서비스에서는 Payload 인터페이스를 원하는 구조로 구현해서 교체하면서 사용할 수 있도록 만들었다.

---

<br>

## **2. Event 생성**

이벤트 생성은 이벤트를 생성하면서 겪었던 시행착오들과 알고있던 디자인 패턴들을 적용하면서 겪었던 생각의 흐름들을 적었다

<br>

### **첫 번째 시도 (Mapper)**

```java
public void createPerformance(PerformanceDto registrationDto) {
	validatePerformanceDate(registrationDto);
	Performance performance = performanceRepository.save(performanceDtoMapper.toEntity(registrationDto));
	**eventPublisher.publishEvent(eventPublisher.publishEvent(createdEventMapper.toDto(performance)));**
}
```

첫 시작은 **createdEventMapper.toDto(performance)** 이 코드부터였다.

공연 서비스에서 이벤트를 발행할 때, Mapstruct을 이용해서 이벤트 클래스로 매핑을 시켜주는 간단한 코드이다. 여기서 들었던 생각은 이러면 ‘**모든 이벤트에 대한 mapper를 다 구현해야 하는거 아닌가**?’ 와 ‘**모든 이벤트는 공통된 구조를 가지고 payload만 바뀔텐데 mapper가 필요할까?**’ 였다.

그래서 다음과 같이 바꿨다.

<br>

### **두 번째 시도 (Factory method 패턴)**

```java
	...
	eventPublisher.publishEvent(createPerformanceEvent(performance));
}

private PerformanceEvent createPerformanceEvent(Performance performance) {
	PerformanceCreatedEventPayload payload = PerformanceCreatedEventPayload.from(performance);
	return PerformanceEvent.from(EventType.PERFORMANCE_CREATED, payload);
}
```

공연 이벤트 클래스와 페이로드 클래스를 따로 만들고 각각 **from**을 이용해서 생성하도록 만들었다.

이 소스를 보며 생각한 것은 ‘**Event 클래스를 생성할 때, 내부적으로 payload 까지 같이 만드는게 낫지 않을까?’** 따로 따로 생성하는 것보다 차라리 ‘**한 번 생성할 때 같이 만드는게 더 가독성있고 관리 차원에서 좋지 않을까?**’ 라는 생각이었다.

<br>

### **세 번째 시도 (Builder 패턴)**

```java
public class PerformanceEventBuilder<T extends PerformanceEvent<Payload>> {
 ...

    public static<T> Builder<T> withEventType(EventType eventType, Performance performance) {
        return new Builder<>(eventType, performance);
    }

    public static class Builder<T> {
        private EventType eventType;
        private Performance performance;
        private T payload;

        public Builder(EventType eventType, Performance performance) {
            this.eventType = eventType;
            this.performance = performance;
        }

        public Builder<T> withPayload(Payload payload) {
            this.payload = payload;
            return this;
        }

        public PerformanceEvent<Payload> create() {
            return PerformanceEvent.from(eventType, payload);
        }
    }
}

private PerformanceEvent<Payload> createPerformanceEvent(Performance performance) {
	return PerformanceEventBuilder.withEventType(EventType.PERFORMANCE_CREATED, performance)
		.withPayload(PerformancePayloadFactory.createdEvent(performance))
		.create();
}
```

생성에 대한 것을 **빌더 패턴**을 적용해서 만들었다. payload 생성에 대한 건 **PerformancePayloadFactory** 클래스에게 책임을 부여했다.

만들고 나서 확인해보니 몇 가지 문제가 있었다.

1. **Builder의 payload를 만들 때 생성 인자는 오직 Performance 만 가능**
2. **payload 없이도 이벤트 생성이 가능**

2번의 경우 코드를 수정해서 payload를 반드시 넣도록 Builder를 수정하면 되지만 Performance만 받을 수 있는 부분은 어떻게 고쳐야 하나 고민을 많이 했다.

<br>

### **네 번째 시도 (Builder & Strategy 패턴)**

```java
public class PerformanceEventBuilder<T extends PerformanceEvent<Payload>> {
    public static<T> Builder<T> withEventType(EventType eventType) {
        return new Builder<>(eventType);
    }

    public static class Builder<T> {
        private EventType eventType;
        private PaylaodCreator payloadCreator; // creator

        public Builder(EventType eventType, Performance performance) {
            this.eventType = eventType;
            this.performance = performance;
        }

        public Builder<T> eventCreator(paylaodCreator paylaodCreator) {
            this.paylaodCreator = paylaodCreator;
            return this;
        }

        public PerformanceEvent<Payload> create() {
                    Payload payload = paylaodCreator.createPayload();
            return PerformanceEvent.from(eventType, payload);
        }
    }

public interface PaylaodCreator {
	Payload createPayload();
}
```

전략 패턴을 적용하여 **PayloadCreator**를 인터페이스를 활용하여 payload를 생성하는 부분의 책임을 Builder에서 분리했다.

하지만 막상 사용하려 보니 각 이벤트에 맞는 Payload를 구현해야 하고 Creator도 구현해야 했다.

쉽게 만들자고 더 복잡해지는 결과로 만들어버렸다. 만약 Payload 값을 만드는 로직이 복잡하다면 이 전략 패턴을 쓰면 되겠지만 지금 정도의 프로젝트에선 너무 과했다.

<br>

### **마지막 시도 (Builder 패턴)**

```java
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

마지막 시도는 **springframework.http.ResponseEntity** 클래스에서 아이디어를 착안했다. 컨트롤러에서 ResponseEntity를 사용할 때는 각각 사용할 수 있는 메서드가 한정되어 있었다. 예를 들어서, 사용자는 `ResponseEntity.ok()` , `ResponseEntity.status()` 와 같이 먼저 상태 값의 정의를 강제했다. 이렇게함으로써 사용자는 상태 값부터 정의할 수밖에 없고 그 다음으로는 **`body()`** 부분이나 **`header()`** 혹은 **`build()`** 를 통해서 바로 객체를 생성할 수 있다.

비록 코드의 내부의 복잡도는 수직 상승했지만 사용했을 때의 편의성이 증가했고 더 직관적이었다. builder에서 처음 사용할 수 있는 메서드는 이벤트 상태를 직관적으로 표현할 수 있도록 **`pending()`** 으로 만들었다. pending() 을 사용하면 자동으로 이벤트는 pending 상태가 되고 나머지 필드값들이 등록된다. 지금은 pending 하나만 넣었지만 **retry, ready, success, fail** 등 원하는 상태를 표현할 수 있다. 또 함수형 인터페이스 **Supplier**를 사용해서 외부에서는 Payload를 만들어서 전달하면 된다.

실제 사용하는 코드는 아래와 같다.

```java
private PerformanceEvent createPerformanceEvent(Performance performance) {
    return PerformanceEventBuilder.pending(EventType.PERFORMANCE_CREATED)
        .payload(() -> PerformanceCreatedPayload.from(performance))
        .create();
}
```

payload는 생성하려는 Payload 구현 클래스를 만들어서 넣어주기만 하면된다.

나중에는 바뀔지 모르겠지만 총 다섯 번의 시도를 통해서 지금은 만족스러운 결과가 나왔다. 실제로 만들어보고 경험해보니 각 시도에는 각자의 장단점이 느껴졌다.

프로젝트 구조나 범용성, 유연성, 복잡도에 따라 맞는 방법을 있을 것이다. 개발은 모든게 트레이드오프라는 말이 조금 와닿았던 경험이었던 것 같다.
