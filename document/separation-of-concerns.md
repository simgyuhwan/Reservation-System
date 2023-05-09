# 관심사의 분리

다음 코드는 공연 정보를 생성하는 코드이다.

**공연 등록 정보**가 들어오면 **검증**을 거치고 **저장**한 뒤 예약 서비스로 **전달**한다(kafka)

```java
public void createPerformance(PerformanceDto registrationDto) {
	validatePerformanceDate(registrationDto); // 검증
	Performance performance = performanceRepository.save(performanceDtoMapper.toEntity(registrationDto)); // 저장
	performanceProducer.sendPerformance(performanceDtoMapper.toDto(performance)); // 예약 서비스로 전달
}
```

예약 서비스에서는 공연 정보를 카프카로 전달받고 이를 예약 공연 정보로 저장한다. 심플하게 동작하는 코드인데.

여기서 알림 서비스를 추가하려고 보니 **어?** 라는 생각이 들었다. 그 이유는 만약 공연 정보를 등록하고 알림을 보내는 로직을 추가고 또 무언가를 추가하다 보면 아래와 같이 추가될 것이다.

```java
public void createPerformance(PerformanceDto registrationDto) {
	validatePerformanceDate(registrationDto); // 검증
	Performance performance = performanceRepository.save(performanceDtoMapper.toEntity(registrationDto)); // 저장
	performanceProducer.sendPerformance(performanceDtoMapper.toDto(performance)); // 예약 서비스로 전달
	....... **알림 서비스로 전달**
	....... **다른 서비스 전달**
}
```

하나의 기능이 추가될 때마다 **공연 정보를 생성하는 책임**만 가져야할 로직에 계속 다른 서비스와의 의존성이 추가되고 여러 가지 책임이 결합되어 테스트는 물론, 나중에는 수정도 힘들어질 것이 보였다.

이러한 주 관심사를 제외한 다른 관심사들을 분리하기 위해 Spring에서 제공하는 **event**를 사용하기로 했다.

## Event Class

먼저 이벤트 클래스이다. 공연이 성공할 때, 발생하는 이벤트 전용 클래스로 처리에 필요한 값을 담아놨다. 필드의 내용은 나중에 수정할 예정이다.

```java
@Getter
public class PerformanceCreatedEvent {
	private LocalDateTime timestamp;
	private Long performanceId;
	private String userId;
	...
}
```

## EventHandler

이벤트 헨들러이다. 이제 **이벤트를 발행**하면 공연 생성된 이벤트에 대한 데이터들은 다 이곳으로 오게된다. 각각의 메서드는 각자의 하는 일이 명확하게 드러날 수 있도록 지었다.

```java
@Component
public class PerformanceCreatedEventHandler {
	private final PerformanceProducer performanceProducer;

  // 예약 서비스로 전송
  @EventListener(PerformanceCreatedEvent.class)
	public void sendToReservationService(PerformanceCreatedEvent performanceCreatedEvent) {
		log.info("Publishing a performance creation event to reservation service");
		performanceProducer.sendPerformance(performanceCreatedEvent);
	}

	// 알림 서비스 전송
	@EventListener(PerformanceCreatedEvent.class)
	public void sendToNotificationService(PerformanceCreatedEvent performanceCreatedEvent) {
		log.info("Publishing performance creation event to notification service");
		// 알림 서비스로 전송
	}
}
```

## PerformanceService

수정된 코드이다. 공연 정보가 저장되면 **mapper**를 통해 값을 변경하여 이벤트를 발행한다.

```java
  private final ApplicationEventPublisher eventPublisher; // 스프링에서 제공하는 이벤트 발행 클래스
	private final CreatedEventMapper createdEventMapper; // MapStruct

	@Override
	public void createPerformance(PerformanceDto registrationDto) {
		validatePerformanceDate(registrationDto);
		Performance performance = performanceRepository.save(performanceDtoMapper.toEntity(registrationDto));
		eventPublisher.publishEvent(createdEventMapper.toDto(performance));
	}
```

## 느슨한 결합

이제 공연 등록 로직에 모여있던 관심사들이 **주 관심사를 제외하고** **분리**되었다. 하지만 여기서 문제가 있다. 이벤트는 **당연히 비동기로 실행될 것 같지만 그렇지 않다. 이벤트는 동일한 스레드로 동기적으**로 실행된다.

그 말인 즉, 공연 등록이 성공했지만 이벤트에서 예외가 던져지면 공연 등록이 롤백된다. 물론 이벤트에 실행되는 로직이 주요한 로직이라면 롤백되는게 맞다.

하지만 공연이 이미 등록 됐다면 공연 정보를 보내는 것과 알림을 보내는 로직 또한 중요하지만 등록된 공연 정보를 통해서 다시 보낼 수 있기 때문에 그 중요도는 상대적으로 낮다고 생각된다.

그러므로 두 가지 설정을 추가했다.

`@Async` 와 `@TransactionalEventListener` 이다. 먼저 `@Async`는 비동기로 새로운 스레드로 새로 생성하여 실행되게 해준다. 그리고 `@TransactionalEventListener`는 기본 값으로 `TransactionPhase.*AFTER_COMMIT`\* 를 가지고 있는데. 이는 이벤트 발행 전의 로직이 트랜잭션 **Commit** 된 후 실행된다는 의미이다.

즉, 공연 등록이 성공하면 **비동기**로 이벤트 로직들이 실행된다는 것이다.

```java
@Async("defaultExecutor")
@TransactionalEventListener
public void sendToReservationService(PerformanceCreatedEvent performanceCreatedEvent) {
	log.info("Publishing a performance creation event to reservation service");
	performanceProducer.sendPerformance(performanceCreatedEvent);
}

@Async("defaultExecutor")
@TransactionalEventListener
public void sendToNotificationService(PerformanceCreatedEvent performanceCreatedEvent) {
	log.info("Publishing performance creation event to notification service");
	// 알림 서비스로 전송
}
```

이제 각 로직들이 서로 독립적으로 작동하여 **느슨한 결합**을 이루었다.

## 오류 처리

공연 등록에서는 성공했지만 이벤트 부분에서 오류가 발생할 수 있다. 현재까지 만든 코드는 아직 이벤트로 전달할 값들이 완전히 결정되지 않았기 때문에 추가하지 않았지만 오류가 발생했을 때 Spring의 **Retry**를 사용해서 복구 할 수 있다.

```java
@Retryable(value = IOException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
public void sendToReservationService(PerformanceCreatedEvent performanceCreatedEvent) throws IOException {
    log.info("Publishing a performance creation event");
    performanceProducer.sendPerformance(performanceCreatedEvent);
}
```

위 코드의 설정된 **@Retryable**의 설정 값들을 간단하게 요약하면 `IOException` 예외가 발생하면 **3번의 재시도**가 실행되며 **1초 간격**으로 재시도를 진행한다.

만약 예외가 발생한 뒤, 복구나 모니터링을 위한 오류 로그를 남기고 싶으면 `@Recover`를 사용하면 된다.

```java
@Recover(value = IOException.class) // IOException에 대한 복구 작업을 수행하는 메서드
public void recover(IOException e, PerformanceCreatedEvent performanceCreatedEvent) {
    // 복구 작업 로직
    // 예) 오류 메시지 전송, 오류 로그 남기기 등
}
```

이벤트 발행에 관련하여 자료를 찾아보면 각 **이벤트를 관리하는 것에도 많은 방법**이 있는 것으로 확인된다. 단순히 내부 로직에 전달하기 위한 이벤트라면 다를 수 있지만 카프카를 통해서 Event를 전달할 경우 표준화된 이벤트 형식이 있어서 관리와 범용성을 높이는 방법도 사용되는 것 같다.

### 참고

[Documenting event-driven APIs with AsyncAPI](https://blog.10pines.com/2022/08/17/documenting-event-driven-apis-with-asyncapi/)

[spring-retry](https://hvho.github.io/2021-12-05/spring-retry)

[Spring-Events-feature](https://findstar.pe.kr/2022/09/17/points-to-consider-when-using-the-Spring-Events-feature/)

[baeldung/spring-events](https://www.baeldung.com/spring-events)
