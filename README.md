# **공연 예약 시스템**

## **목차**

#### [1. 프로젝트](#프로젝트-설명-1)

#### [2. 기술 스택](#2-기술-스택-1)

#### [3. 아키텍처](#3-아키텍처-1)

#### [4. Document](#4-document-1)

#### [5. 요구사항](#5-요구사항-1)

#### [6. 이슈](#6-이슈-1)

<br>

---

# **1. 프로젝트**

## 프로젝트 개요
대세는 MSA, DDD, 멀티모듈이라고 하는데. '정말로 좋을까?'라는 의구심에 시작하게된 프로젝트입니다. 실제로 MSA로 프로젝트를 설계해보면서 각각의 장단점을 경험해보고자 합나다. 

### 목표

- MSA로 Spring Cloud를 이용하여 온라인 서비스를 구현한다.
- 각 서비스는 멀티 모듈을 사용하여 모듈화를 진행한다.
- 각 서비스들을 연동할 때 적합한 방법을 확인하고 연습한다.
- 대부분의 코드는 테스트 코드를 구현한다.
- 무엇이 정답인지 모를 땐, 다 해보자.

### 기능 요약

- 공연자 혹은 기획자는 공연 정보를 등록, 수정, 공연 일정 및 가격 설정 기능을 통해 공연을 등록할 수 있다.
- 공연자 혹은 기획자는 등록된 공연의 예약 현황을 조회할 수 있다.
- 사용자는 등록된 공연 중 원하는 공연을 선택하여 예약하거나 취소할 수 있다.

### 구조

- 각 서비스간의 연동은 Rest API와 이벤트를 이용한다.
- 모든 도메인은 각각의 DB를 가진다.
- 발생되는 이벤트들은 모두 이벤트 서비스로 전달되며, 이벤트 서비스는 이벤트를 발행 및 관리하는 역할을 한다.
- 분산 트랜잭션은 사가 패턴의 Orchestration, Choreography 두 방식을 다 사용한다
- 공연 관련은 Choregraphy 방식, 예약 신청은 Orchestration 방식을 사용한다.
- 보안 구성으로는 Authorization Server, Resource Server를 구성한다.
- Gateway Server를 Resource Server로 구성하고 모든 요청은 Gateway Server를 통해서만 가능하게 한다.
- Gateway Server를 거치지 않고는 다른 서비스들에 직접적인 접근이 불가능하도록 설정한다.

<br>

# **2. 기술 스택**

### MSA

- Spring Cloud 2022.0.2
- Spring Cloud Gateway
- Eureka Server
- Eureka Client
- FeignClient 4.0.0
- Resilience4j 3.0.0

### 백엔드

- Java 17
- Spring Boot 3.0.6
- Spring Data JPA 3.0.4
- MapStruct 1.5.2
- QueryDsl 5.0.0

### 메시징

- Kafka 3.3.2

### 캐싱

- Redis 3.0.5

### 모니터링

- Micrometer Tracing
- Prometheus 2.37.7
- Zipkin 2.24
- Grafana 9.5.1

### 컨테이너화

- Docker

### 데이터베이스

- MySQL 8.0
- PostgreSQL 14.1

<br>

# **3. 아키텍처**

![Structure](./document/image/structure_v6.png)

<br>

# **4. Document**

#### \* [API 문서](./document/api-document.md)

#### \* [기능별 테스트 케이스](./document/test-case.md)

<br>

# **5. 요구사항**

## 1. 사용자 기능

- 회원가입 기능(기획자, 일반)
- 로그인, 로그아웃 기능
- 사용자 정보 수정, 조회, 회원 탈퇴 기능
- 예약 가능한 공연 조회 기능
- 원하는 공연 선택 예약 기능
- 예약 정보 확인, 수정, 취소 기능

## 2. 기획자 기능

- 공연 정보 등록 및 수정 기능
- 공연 일정 및 가격 설정 기능
- 등록한 공연 예약 현황 조회 기능
- 공연 취소 또는 변경 알림 발송 기능
- 기획자별 공연 정보 조회 기능

## 3. 관리자 기능

- 전체 예약 내용 조회, 수정, 취소 기능

## 4. 시스템 기능

- 예약 정보, 결제 정보, 취소 정보 Kafka 이벤트 발행 기능
- 이벤트 수신 서비스 내부 데이터 업데이트 기능
- 예약 가능한 공연 관리 기능
- 예약, 결제 로그 기록 기능

## 5. 알림 기능

- 사용자에게 예약, 취소 알림 전송

<br>
<!-- 
# **6. 이슈**

#### [1. 예약 신청이 동시에 엄청난 양이 온다면?](./document/many-reservation.md)

#### [2. 관심사의 분리](./document/separation-of-concerns.md)

#### [3. 모니터링 구성(Local + Docker)](./document/monitoring-docker-local.md)

#### [4. 이벤트 클래스를 설계하면서](./document/event-class-design.md)

#### [5. 멀티 모듈을 구성하면서](./document/configure-multi-module.md)

#### [6. 사가패턴 적용해보기(Choreography)](./document/choreography.md)

#### [7. 사가패턴 적용해보기(Orchestration)](./document/orchestration.md) -->
