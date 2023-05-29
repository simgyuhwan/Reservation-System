# 1. 사가 패턴

먼저 **트랜잭션**이란, 데이터베이스를 변경하는 하나의 작업 단위이다. 모놀로틱 환경에서는 하나의 DB에서 이루어지고 ACID를 지원하기 때문에 트랜잭션 관리가 비교적 쉽다.

하지만 MSA 환경에서는 도메인마다 DB를 관리한다. 각각의 트랜잭션이 분리되어 있기 때문에 하나의 흐름으로 제어하기 위해서는 별도의 관리가 필요하다. 이렇게 분산되어 있는 트랜잭션을 분산 트랜잭션이라 한다.

 **분산된 트랜잭션을 관리하기 위한 방법으로는 사가패턴**이 있는데. 사가 패턴이란, 순차적으로 도메인별로 실행되는 로컬 트랜잭션을 관리하기 위한 패턴이다. 이때 서비스들의 통신은 이벤트를 통한 비동기 방식으로 전달한다.

 이 사가 패턴에도 두 가지 방식이 존재한다. 

1. **Choreography 방식**
2. ****Orchestration 방식****

이 두 가지가 있는데. 이 글은 Choreography 방식의 개념을 보고 적용해본면서 느낀점을 적어보려고 한다.

<br>

## 1) ACID

트랜잭션은 **ACID(원자성, 일관성, 격리성, 영속성)을 보장**하는데. 사가 패턴은 **격리성, 일관성을 보장하기 어렵다.**

격리성이란 하나의 트랜잭션이 다른 트랜잭션에 영향을 미치지 말아야 한다. 트랜잭션이 서로 격리되어 있어 독립성을 보장해준다. 그런데 분산된 트랜잭션을 관리하는 환경에서는 쉽지 않다. 예를 들어보자. 상품의 상태를 변경하는 요청이 왔을 때, 재고 서비스에 이 변경된 요청을 전달한다고 가정해보자.

— {상품 상태 변경 요청} → **상품 서비스** — {재고 상태 변경 요청} → **재고 서비스**

**상품 서비스**에서는 **‘상품 상태 변경 요청’** 을 받게된다. 그리고 상품의 상태가 변경될 것이다. 이 변경된 데이터는 DB에 커밋되어 저장될 것이고 이어서 **재고 서비스**에 **‘재고 상태 변경 요청’**을 발행한다. 

**재고 서비스**에서는 **‘재고 상태 변경 요청’** 이벤트가 전달될 것이다. 이벤트를 전달받은 재고 서비스는 요청에 대한 프로세스를 진행한다. 재고서비스에서는 재고에 대한 개수의 변경이나 이름의 변경이 일어날 수 있다. 

그런데 재고 서비스에서 작업도중 ***장애가 발생***하여 ***보상 트랜잭션을 진행**한다고 가정해보자.* 재고 서비스에서는 이전 작업에 대한 롤백을 진행할 것이고 **상품 서비스**에 **‘롤백 요청 이벤트’**를 발행한다. 

재고 서비스(장애 발생) — {롤백 요청} → 상품 서비스

하지만 이때 ***롤백 이전에 사용자가 커밋된 상품 상태를 조회하면 어떻게 될까?*** 그럼 사용자에게는 **롤백 전의 상태를 조회하게 된다. 롤백 전의 상태는 잘못된 상태이기 때문에 사용자에게 적절하지 못한 정보를 제공해주는 것과 같다. 

이와 분산 트랜잭션에서는 **격리성의 부족으로 인해 일관성을 보장하지 못하게 될 수 있다.**

구현하는 과정에서 이러한 일관성의 부족은 받아드리기로 했고 **최종 일관성**을 보장하는 방식으로 처리했다. 최종 일관성이란, 데이터의 일관성이 즉시 보장되지 않는다. 어느 정도의 지연 시간이나 오차가 발생할 수 있지만 결국에는 일관된 상태로 수렴하는 것을 의미한다.

<br>

## 2) Choreography

**choreography** 방식이란, 중앙에서 제어하는 시스템 없이 서비스들이 직접 서로 이벤트를 구독하고 발행하여 트랜잭션을 관리하는 방식이다. 예를 들어, 아래와 같은 흐름이 될 수 있다. 

**주문 요청 → 주문 서비스 → 재고 서비스 → 결제 서비스** 

주문 서비스에서 트랜잭션을 커밋하고 완료하면 재고 서비스에 전달하고 재고 서비스에서 트랜잭션이 완료되면 결제 서비스로 전달한다. 장애가 났을 때는 반대로 롤백 이벤트를 진행한다. 이벤트는 kafka를 이용했다. 

<br>

# 2. Choreography 적용해보기

공연 서비스에서 시작되는 공연 등록, 수정에 대한 처리를 **Choreography 방식**으로 처리했다. **Choreography 방식**의 예시 코드를 찾기는 어려웠다. 하지만 구글링을 통해서 개념에 대한 내용은 많이 읽어볼 수 있었다. 

**공연 서비스는 등록과 수정에 대해 예약 서비스에 정보를 전달**한다. 공연에 대한 정보를 저장하면 예약 서비스는 예약 가능한 공연 정보와 좌석 정보를 저장한다.

<br>

## 1) **이벤트 모듈화**

공연 서비스에 이벤트를 적용하기 앞서 이벤트 처리의 구분을 위해 이벤트 모듈을 구성했다. 

```java
├─out
│  └─production
│      ├─classes
│      │  └─com
│      │      └─sim
│      │          └─performance
│      │              └─event
│      │                  ├─core
│      │                  │  ├─payload
│      │                  │  └─type
│      │                  ├─external
│      │                  └─internal
```

기본 구조는 **core**에는 핵심이 되는 클래스를 모아놨다. 그리고 외부 이벤트와 내부 이벤트를 분리했다. 모듈을 분리하고 도메인에서의 처리가 필요한 handler는 domain 서비스에 구현했다. 

### **내부 이벤트**

```java
public class InternalEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 공연 생성 내부 이벤트 발행
     */
    public void publishPerformanceCreatedEvent(InternalEventDto internalEventDto) {
        log.info("Creation of performances Publication of internal events. performanceId : {}", internalEventDto.getPerformanceId());
        eventPublisher.publishEvent(PerformanceCreatedPayload.from(internalEventDto));
    }

    /**
     * 공연 생성 결과, 내부 이벤트 발행
     */
    public void publishPerformanceCreatedEventResult(EventResult eventResult) {
        log.info("Creation of performances Publication of internal events result. eventId : {}", eventResult.getId());
        eventPublisher.publishEvent(CreatedEventResultDto.from(eventResult));
    }
}
```

내부 이벤트 발행에 대한 처리를 진행하는 클래스이다. 내부 이벤트들의 필드의 차이는 없을 것이라 예상하여 하나로 통일하고 각각 메서드명으로만 분리했다. 만약 필드의 차이가 있다면 오버로딩으로 변경할 것이다.

공연 생성 내부 이벤트가 발행되면 두 가지 작업이 진행된다. 

- **이벤트 저장**
- **외부 이벤트 발행**

이벤트의 발행과 결과를 조회하기 위해서 이벤트를 저장하고 다른 서비스에게 이벤트를 전달하기 위한 외부 이벤트 발행이 이루어진다. 

### **이벤트 저장**

```java
public class InternalEventHandler {
	private final PerformanceEventService performanceEventService;

	/**
	 * 공연 생성 이벤트 저장
	 */
	@EventListener
	public void saveEvent(PerformanceCreatedPayload performanceCreatedPayload) {
		performanceEventService.saveEvent(performanceCreatedPayload, EventType.PERFORMANCE_CREATED);
	}
}
```

도메인 모듈에 구성된 내부 이벤트 핸들러에서는 이벤트를 받아서 DB에 저장한다. 

### **외부 이벤트**

```java
public interface ExternalEventPublisher {
	/**
	 * 공연 생성 이벤트 발행
	 */
	void publishPerformanceCreatedEvent(PerformanceEvent performanceEvent);

	/**
	 * 공연 수정 이벤트 발행
	 */
	void publishPerformanceUpdatedEvent(PerformanceEvent performanceEvent);
}
```

외부 이벤트 발행은 메시지 큐를 사용하는 **RabbitMQ**나 **kafka**를 사용하는 다른 구현체를 사용할 수 있도록 인터페이스로 구현했다. 실제 외부 이벤트 발행은 **kafka**로 진행했다. 

```java
public class KafkaExternalEventPublisher implements ExternalEventPublisher {
	private final StreamBridge streamBridge;

	/**
	 * 외부 서비스에게 공연 생성 이벤트 발행
	 *
	 * @param performanceEvent 공연 이벤트
	 */
	@Override
	public void publishPerformanceCreatedEvent(PerformanceEvent performanceEvent) {
		log.info("Publish events to external services : {}", performanceEvent);
		streamBridge.send("performance-service.performance.created", performanceEvent);
	}
}
```

<br>

## 2) **공연 등록**

공연 등록을 **Choreography 방식**으로 설계하면서 고민했던 내용은 다음과 같다.

- **이벤트로 전달할 값**
- **보상 트랜잭션**
- **멱등성**

### **이벤트로 전달할 값**

공연 서비스에서 공연 등록 신청이 완료되면 예약 서비스로 이벤트가 발행한다. 예약 서비스에서는 공연 정보를 가지고 공연 가능한 예약 정보와 좌석 정보를 저장한다. 이때 **예약 서비스로 전달할 이벤트 내부 값**이 문제였다.

처음에는 단순히 이벤트에 *공연 정보를 모두 담아서* 전달했다. 필요한 데이터가 모두 있었기 때문에 처리 시 문제는 없었다. 하지만 다른 작업의 수정을 통해 데이터의 변경이 잦았고 그때마다 관여된 모든 필드를 수정하는 작업이 이루어졌었다. 이 이벤트 내부 값도 그 중에 하나였는데. 하나하나 다 수정하는 작업은 너무 힘들었다.

**너무 비효율적인 설계**인 듯 싶어 대안이 필요했고 구글링의 결과, [우아한 형제들의 블로그](https://techblog.woowahan.com/7835/)를 읽고 바로 적용해보기로 했다. **zero payload** 방식으로, 이벤트 처리를 위한 페이로드에 필요한 **식별 값**만 포함시킨다. 

**공연 서비스 → 예약 서비스**

```java
public class PerformanceCreatedPayload implements Payload {
	private String id;
	private Long performanceId;
	private Long memberId;
}
```

공연 서비스에서 예약 서비스에는 이벤트 처리에 필요한 **식별값**을 포함해서 전달해준다. 이제 예약 서비스는 포함된 공연 ID 값을 사용해서 **REST API** 요청을 진행한다. 

```java
@GetMapping("/{performanceId}/status/pending")
public PerformanceInfoResponse unregisteredPerformanceInfo(@PathVariable Long performanceId) {
	PerformanceDto performanceDto = performanceQueryService.selectPendingPerformanceById(performanceId);
	return PerformanceInfoResponse.from(performanceDto);
}
```

공연 서비스에는 현재 공연 등록 신청 대기중인 공연 정보를 조회하는 API를 추가해놨다. 이 API 를 통해서 예약 서비스는 필요한 정보를 조회하여 이벤트를 처리한다. 

이 방법을 통해 **코드의 변경점을 최소화**할 수 있었다.

### **보상 트랜잭션**

이벤트의 시작점인 공연 서비스에서 실패한다고 가정해보자. 이곳은 간단히 어노테이션을 추가함으로써 로컬 트랜잭션 관리가 되기 때문에 **장애가 발생 시 간단하게 롤백할 수 있다**. 

하지만 커밋이 완료된 후, 공연 서비스에서 이벤트를 발행하고 예약 서비스에서 장애가 발생한다고 해보자. 이때는 이미 공연 서비스에서 커밋 된 내용을 롤백 처리해야 한다. 이러한 보상 트랜잭션 처리에 관해서도 고민을 많이 했다.

공연 등록 시, 등록 처리로 끝내는게 아니라 등록 대기상태를 추가할까? 아니면 롤백할 때 기존 등록 데이터를 삭제할까? 

결과적으로 등록 대기 상태를 추가하기로 했다. 공연 Entity에 상태 값을 추가했다. 공연 등록 시 공연 정보는 Pending 상태로 저장한다. 

```java
public enum RegisterStatusType {
    PENDING("등록 요청 대기중"),
    COMPLETED("등록 완료"),
    FAILED("등록 실패");
		...
}
```

사용자는 따로 추가한 API를 통해서 공연 등록 상태를 조회할 수 있게 했다. 아래의 API를 통해서 현재 등록 신청 대기, 완료, 실패 여부를 조회할 수 있다.

```java
@GetMapping("/{performanceId}/status")
public PerformanceStatusDto performanceStatus(@PathVariable Long performanceId) {
	return performanceQueryService.getPerformanceStatusByPerformanceId(performanceId);
}
```

보상 트랜잭션을 적용하면서 사용자 친화적으로 만들 수 있도록 노력했다. 일반적인 공연 조회에서는 상태가 **COMPLETE** 인 공연만 조회하도록 변경했으며 예약 서비스에서 실패시, 롤백 진행 결과로 **FAILED** 상태로 변경한다. 사용자는 **FAILED** 된 공연을 조회하면 재등록 요청 메시지를 받게 된다. 이 밖에도 이벤트 발행과 처리는 모두 저장되도록 구현했다.

### **멱등성**

**멱등성**이란, **여러번 동일한 요청을 받아도 동일한 결과를 반환하는 것**을 의미한다. 만약 동일한 이벤트가 여러개가 전달된다면 전달된 수 만큼 이벤트가 처리될 것이다. 이러한 처리를 방지하기 위해 **이벤트 응답에 대해서 처리 결과 저장과 조회시 캐시**를 이용하기로 했다. 만약 동일한 요청이 여러 번 온다면 *캐시 값을 조회하여 반환하게 되고 캐시에 없다면 DB를 조회해서 반환해준다*.

```java
public EventResult savePerformanceInfo(DefaultEvent<PerformanceEventPayload> event) {
	String eventId = event.getId();
	Payload payload = event.getPayload();

	**// 처리된 이벤트라면 이전 처리 결과 반환**
	if(isProcessedEvent(eventId)) {
		return getProcessedEventResult(eventId);
	}
	**// 이벤트 처리**
	return processCreatedEvent(eventId, payload);
}
```

<br>

## 3) **공연 수정**

공연 등록의 경우는 단순히 **공연 등록 대기 상태**로 바꾸는 것으로 손쉽게 해결이 가능했다. 하지만 공연 수정은 쉽지 않았다.

그 이유는 공연 정보가 수정이 되고 커밋이 됐다고 해보자. 예약 서비스에서 실패한다면 보상 트랜잭션을 진행할 것이다. 이때 공연 서비스에서는 수정되기 전 데이터로 롤백해야 한다. 그런데 ***이 수정 전의 데이터는 어디있는가***? 

커밋 되기 전의 데이터가 존재해야 롤백이 가능한데. 이미 변경된 데이터밖에는 조회할 수 없었다. 그러면 이 변경 전의 데이터를 저장할 수 밖에 없는데. 어디에 어떻게 저장해야할까??

한참을 고민했고 결과적으로는 세 가지 방안을 떠올렸다.

### **임시 저장소**

동일한 DB의 다른 테이블이나 NoSQL에 이러한 정보를 저장하는 방법이었다. 캐시를 사용하자니 유실됐을 때의 손실이 크기 때문에 제외했다.

동일한 DB의 다른 테이블에 저장하는 방법은 테이블의 스키마를 어떻게 구성해야 할지였다. 모든 테이블이 복합적으로 사용할 수 있게 만들어야 하는건가? 아니면 각각 하나씩 만들어야 하는건가? 아무리 생각해도 비효율이었다.

NoSQL로 구성하는 방법은 모든 서비스에서 사용하게 해야 하나? 아니면 각 서비스에 하나씩 구성해야할까? 그러면 각 서비스 당 두 개의 DB를 사용하게 될텐데. 너무 과하지 않을까 싶었다. 실 사용에 대한 경험이 없었기 때문에 섣불리 해볼 수 없었다.

### **버전**

임시 저장소를 떠나보내고 다른 방안을 떠올렸는데. **공연 Entity의 식별 값과 버전을 복합키**로 사용하는 방법이다. 공연에 대한 수정 작업이 있을 때 버전을 올려서 저장한다. 그리고 조회 시 최신 버전만 조회한다.

만약 **롤백해야 한다면 이전 버전으로 돌려주기만 하면 된다**. 가장 괜찮은 방법으로 생각되었다. 하지만 이전 작업으로 인한 피로로 인해 이 방법은 다음으로 미뤘다.

### **최종 일관성**

**스케줄러를 통한 이벤트 재발행**을 통해서 **최종 일관성을 보장**한다. 공연 서비스에서 공연 수정이 완료되었다면 연관된 서비스에서 장애가 나더라도 재전송을 통해 최종적으로 일관성을 보장하게 했다. 

예약 서비스에서 이벤트 실패 시, 공연 서비스에선 해당 이벤트를 **RETRY 상태**로 변경한다. 그리고 스케줄러를 통해 이벤트 타입과 상태를 통해 재발행해야 할 이벤트를 조회하여 다시 발행한다.

그 잠깐 사이의 정보의 오류는 일단 감수하기로 했다. 스케줄러로 이벤트를 재발행하기 전에 모니터링을 통해 장애를 확인하고 처리해야 하기 때문에 장애에 대한 저장 처리를 추가했다. 이 부분은 간단하게만 작성했다.

```java
/**
 * 공연 수정 Retry 이벤트 재발행
 */
@Scheduled(cron = "0 0/10 * * * *")
public void rePublishUpdateEvent(){
	performanceEventService.rePublishPerformanceUpdateEvent();
}
```

이 부분의 아쉬웠던 점은 스케줄러를 모듈로 분리하고 처리하고 싶었으나 도메인에서 이벤트를 관리하도록 해버려서 어려웠다. 조금 더 고민해야할 부분이다.

# **느낀점**

해보면 할 수 있겠지라고 단순하게 생각했던 분산 트랜잭션에 대해 생각해야 할 것이 너무나 많다는 것을 느꼈다. 개념을 읽고 필요한 부분의 해결을 찾아보고 상상하며 구현했지만, 실제로 다른 방법이 있나 찾아보니 다양한 방법을 찾을 수 있었다. 그리고 다시금 느끼는 것이 경험을 통해 어느 정도의 설계를 하고 시작하는게 좋겠구나 싶다. 이번 프로젝트에서는 일단 시작하고 찾아가며 구현하다보니 너무나 잦은 변경이 이루어졌다. 물론 덕분에 변경을 최소화하기 위한 고민을 할 수 있어서 도움이 됐다.

### **참고**

https://learn.microsoft.com/ko-kr/azure/architecture/reference-architectures/saga/saga

https://azderica.github.io/01-architecture-msa/