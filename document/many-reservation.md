# 예약 신청이 동시에 엄청난 양이 온다면?

예약 서비스를 마이크로 서비스로 여러 개를 동시에 띄우고 신청을 받는다고 가정한다. 예약 서비스에 동일한 시간에 예약 신청이 엄청난 양으로 온다면 동시성 문제가 발생할 것이다. 
또 예약 신청이 올 때마다 DB에서 좌석 수를 조회하는 것은 성능상 좋지 않고 여러 마이크로 서비스로 예약 서비스를 구성했다면 요청마다의 순서를 보장해야 하는데, 만약 단일 프로세스를 사용중이라면 **synchronized** 키워드를 사용해서 임계 영역(critical section)을 설정하여 한 번에 하나의 스레드만의 접근을 허용하면 된다. 하지만 현재 가정된 상황은 여러 프로세스가 동시에 하나의 자원에 접근 중인 상황이다. 

기타 방법으로 비관락, 낙관적락, 분산락에 대해 많은 자료를 찾아봤지만 이 상황에 가장 잘 맞는 것은 분산락으로 판단 되어 **분산락**을 사용하기로 했다.

코드는 *모든 코드가 아닌 필요하다고 생각되는 코드만 작성했다.* 

예약 신청할 때는 **캐시**를 조회하여 예약 신청 가능 여부만 확인하고 가능할 때만 로직을 처리하고 신청이 불가능하면 예외를 던지는 것으로 처리하기로 했다. 아래는 로직 처리에 대한 흐름도다.

---

## **흐름도**

1. 예약 서비스에서 예약 신청
2. Redis에서 예약 신청 여부를 확인
    - 좌석이 예약 가능한 경우:
        1. Redis에 락 획득
        2. 데이터베이스에 좌석 예약 정보를 업데이트
        3. Kafka에 예약 정보 업데이트
        4. Redis의 락을 해제
    - 좌석이 예약 불가능한 경우:
        1. 예약 불가능한 상태를 호출한 서비스에 예외로 반환

캐시서버는 위에 적은 대로 **Redis**를 사용했다. 많은 서비스가 구성되어 있다고 가정해서 Redis Cluster로 구성하였다.

## **기존 코드**

먼저 평범하게 작성한 예약 신청 로직이다. 유효성 검증을 하고 좌석을 예약 한 뒤, 변경 사항은 update 한다. 그리고 예약 도메인을 저장하고 예약 정보를 반환한다. 

```java
public ReservationInfoDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto) {
    PerformanceInfo performanceInfo = findPerformanceById(performanceId);
    PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);

    validationReservation(performanceInfo);
    schedule.reserveSeat();

    Reservation reservation = reservationRepository.save(Reservation.of(reservationDto, schedule));
    return ReservationInfoDto.of(reservation, schedule, performanceInfo.getName());
}
```

### **RedisConfig**

Redis의 직접적인 접근을 위해 `RedisTemplate`를 `Bean`에 등록했다. 

```java
public class RedisConfig {
	private final RedisClusterConfigurationProperties clusterProperties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		//...cluster 적용
	}

	@Primary
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		return redisTemplate;
	}
}
```

Redis에 저장할 값이 중요한데. 이 부분은 고민을 많이 했다. 객체 전체를 캐시에 저장해서 나중에 수정이 될 때 객체를 그대로 저장하여 보다 나은 유지보수성을 위하는게 좋을까? 아니면 필요한 값만 저장해서 조회하고 업데이트 할까?

고민해봤지만 다른 곳에서 사용되지 않을 것이라고 판단되어 필요한 부분만 캐시에 저장하기로 했다. 

```java
HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
Boolean isAvailable = (Boolean)hashOperations.get(PERFORMANCE_SCHEDULE + scheduleId, "isAvailable");
```

캐시의 저장은 Redis의 Hashs 자료구조를 사용했다. `PERFORMANCE_SCHEDULE + scheduleId` 와 `isAvailable` 의 필드명을 키로 설정했다.

만약 `isAvailable`이 **null** 이라면 캐시에 등록된 것이 없기 때문에 DB에 조회 후 저장했다.

```java
if (isAvailable == null) {
  PerformanceSchedule schedule = performanceScheduleRepository.findById(scheduleId)
    .orElseThrow(RuntimeException::new);
  hashOperations.put(PERFORMANCE_SCHEDULE + scheduleId, "isAvailable", schedule.isAvailable());
  isAvailable = schedule.isAvailable();
}
```

조회한 `isAvailable`이 **false** 라면 예약 신청이 불가능하기 때문에 **매진 예외**를 던진다. 

```java
if (!isAvailable) {
 throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, scheduleId);
}
```

이제 예약 신청이 가능하다면 **분산락**을 적용할 차례다. 분산락은 Redisson을 사용했다. Redisson의 pubsub 기능을 사용하여 락을 획득 할 수 있을 때만 알림을 받기 때문에 성능에 더 좋다.

```java
RLock lock = redissonClient.getLock(SEAT_LOCK);

try {
  if (!(**lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS**))) {
    throw new RuntimeException("Seat Lock 획득 실패");
  }

  .. 비즈니스 로직

  hashOperations.put(PERFORMANCE_SCHEDULE + scheduleId, "isAvailable", schedule.isAvailable());

  ...
  return ReservationInfoDto.of(reservation, schedule, performanceInfo.getName());
} catch (InterruptedException e) {
  throw new RuntimeException(e);
} finally {
  lock.unlock();
}
```

위의 설정을 통해 **WAIT_TIME** 까지 대기하며 **LEASE_TIME**이 지나면 락이 해제된다. 대기 시간전에 `lock.tryLock`을 통해 락을 획득하면 분산락을 적용하여 비즈니스 로직이 실행된다. 

---

## **전체 코드**

```java
public ReservationInfoDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto) {
  HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
  Boolean isAvailable = (Boolean)hashOperations.get(PERFORMANCE_SCHEDULE + scheduleId, "isAvailable");

  if (isAvailable == null) {
    PerformanceSchedule schedule = performanceScheduleRepository.findById(scheduleId)
      .orElseThrow(RuntimeException::new);
    hashOperations.put(PERFORMANCE_SCHEDULE + scheduleId, "isAvailable", schedule.isAvailable());
    isAvailable = schedule.isAvailable();
  }

  if (!isAvailable) {
    throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, scheduleId);
  }

  RLock lock = redissonClient.getLock(SEAT_LOCK);

  try {
    if (!(lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS))) {
      throw new RuntimeException("Seat Lock 획득 실패");
    }

    PerformanceInfo performanceInfo = findPerformanceById(performanceId);
    PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);

    validationReservation(performanceInfo);
    schedule.reserveSeat();

    hashOperations.put(PERFORMANCE_SCHEDULE + scheduleId, "isAvailable", schedule.isAvailable());

    Reservation reservation = reservationRepository.save(Reservation.of(reservationDto, schedule));
    return ReservationInfoDto.of(reservation, schedule, performanceInfo.getName());
  } catch (InterruptedException e) {
    throw new RuntimeException(e);
  } finally {
    lock.unlock();
  }
}
```

## **리팩터링**

리팩터링은 Spring에서 제공하는 Cache 추상화 기능을 사용했다. 보다 복잡하거나 다양한 설정을 할거라면 RedisTemplate를 쓰는게 맞지만 그정도까지 사용할 필요가 없기에 Spring Cache로 변경했다. 캐시를 저장할 때도 전체 데이터를 넣을지 말지를 아직도 고민했지만 동일하게 필요한 부분만 저장하기로 결정했다.

```java
public ReservationInfoDto createReservation(Long performanceId, Long scheduleId, ReservationDto reservationDto) {
  Boolean isReservable = getReserveAvailability(scheduleId);

  if (!isReservable) {
    throw new SoldOutException(ErrorMessage.SOLD_OUT_PERFORMANCE, scheduleId);
  }

  RLock lock = redissonClient.getLock(SEAT_LOCK);

  try {
    if (!(lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS))) {
      throw new RuntimeException("Seat Lock 획득 실패");
    }

    PerformanceInfo performanceInfo = findPerformanceById(performanceId);
    PerformanceSchedule schedule = findPerformanceSchedule(performanceInfo, scheduleId);

    validationReservation(performanceInfo);
    schedule.reserveSeat();

    updateReserveAvailability(scheduleId, schedule.isAvailable());

    Reservation reservation = reservationRepository.save(Reservation.of(reservationDto, schedule));
    return ReservationInfoDto.of(reservation, schedule, performanceInfo.getName());
  } catch (InterruptedException e) {
    throw new RuntimeException(e);
  } finally {
    lock.unlock();
  }
}

@Cacheable(value = "performance-reserve-availability", key = "#scheduleId")
public Boolean getReserveAvailability(Long scheduleId) {
	return performanceScheduleRepository.findById(scheduleId)
		.orElseThrow(() -> new PerformanceScheduleNotFoundException(ErrorMessage.PERFORMANCE_SCHEDULE_NOT_FOUND, scheduleId))
		.isAvailable();
}

@CachePut(value = "performance-reserve-availability", key = "#scheduleId")
public Boolean updateReserveAvailability(Long scheduleId, Boolean isAvailable) {
	return isAvailable;
}
```


## **참고**
[낙관적과 비관적 락](https://sabarada.tistory.com/175)   
[트랜잭션 락과 2차 캐시](https://happyer16.tistory.com/entry/16%EC%9E%A5-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EA%B3%BC-%EB%9D%BD-2%EC%B0%A8-%EC%BA%90%EC%8B%9C)  
[레디스를 활용한 분산락](https://hyperconnect.github.io/2019/11/15/redis-distributed-lock-1.html)  
[레디스 설치 및 Redisson 분산락 구현](https://it-hhhj2.tistory.com/102)