# **공연 예약 및 결제 시스템**

## **목차**

#### [1. 프로젝트 개요](#프로젝트-개요)

#### [2. 기술 스택](#2-기술-스택)

#### [3. 아키텍처](#3-아키텍처)

#### [4. Document](#4-document)

#### [5. 요구사항](#5-요구사항)

#### [6. 주요 포인트](#6-주요-포인트)

<br>

---

# **1. 프로젝트 개요**

MSA 아키텍처를 이용해서 간단한 하나의 시스템을 만들어 보는 것을 목표로 합니다. 해당 시스템은 공연 예약 및 결제에 대한 전반적인 기능을 제공하는 온라인 서비스입니다.
공연자 혹은 기획자는 공연 정보를 등록, 수정, 공연 일정 및 가격 설정 기능을 통해 공연을 등록할 수 있습니다. 또 등록된 공연의 예약 현황을 확인할 수 있습니다.
사용자는 등록된 공연 중 원하는 공연을 선택하여 예약과 결제를 할 수 있습니다.
관리자는 모든 예약 내용을 조회하고 수정 또는 수정할 수 있습니다.

<br>

# **2. 기술 스택**

```
Java 17
Spring Boot 3.0.4
Kafka
Spring Cloud
MSA architecture
Redis
Docker
MySQL
PostgreSQL
```

<br>

# **3. 아키텍처**

<br>

# **4. Document**

#### \* [API 문서](https://github.com/simgyuhwan/Reservation-and-Payment-System/blob/master/document/api-document.md)

#### \* [기능별 테스트 케이스](https://github.com/simgyuhwan/Reservation-and-Payment-System/blob/master/document/test-case.md)

#### \* [설치 및 실행 방법](https://github.com/simgyuhwan/Reservation-and-Payment-System/blob/master/document/install-document.md)

<br>

# **5. 요구사항**

## 1. 사용자 기능

- 회원가입 기능(기획자, 일반)
- 로그인, 로그아웃 기능
- 사용자 정보 수정, 조회, 회원 탈퇴 기능
- 예약 가능한 공연 조회 기능
- 원하는 공연 선택 예약 기능
- 예약 정보 확인, 수정, 취소 기능
- 결제 및 결제 정보 확인 기능

## 2. 기획자 기능

- 공연 정보 등록 및 수정 기능
- 공연 일정 및 가격 설정 기능
- 등록한 공연 예약 현황 조회 기능
- 공연 취소 또는 변경 알림 발송 기능
- 기획자별 공연 정보 조회 기능
- 공연 예약 현황을 통한 수익 관리 기능(보류)
- 결제 내역 및 정산 기능(보류)

## 3. 관리자 기능

- 전체 예약 내용 조회, 수정, 취소 기능
- 결제 내역 조회 기능
- 공연 정보 승인 또는 거절 기능

## 4. 시스템 기능

- 예약 정보, 결제 정보, 취소 정보 Kafka 이벤트 발행 기능
- 이벤트 수신 서비스 내부 데이터 업데이트 기능
- 예약 가능한 공연 관리 기능
- 결제 처리 및 예약, 결제 로그 기록 기능

## 5. 알림 기능

- 사용자에게 예약, 결제, 취소 알림

<br>

# **서비스 및 API**

## 1. 회원 서비스

    회원가입, 로그인, 로그아웃, 회원정보 수정, 조회, 회원 탈퇴

### 회원 API

- POST /signup: 회원가입 요청
- POST /login: 로그인 요청
- POST /logout: 로그아웃 요청
- GET /members/{userId}: 사용자 정보 조회 요청
- PUT /members/{userId}: 사용자 정보 수정 요청
- DELETE /members/{userId}: 사용자 회원 탈퇴 요청

## 2. 공연

    공연 정보 등록, 수정, 공연 일정 및 가격 설정, 등록된 공연 조회,
    공연 예약 현황 조회, 공연 취소 또는 변경 알림 발송 기능

### 공연 API

- POST /performances: 공연 정보 등록 요청
- PUT /performances/{performanceId}: 공연 정보 수정 요청
- GET /performances: 등록된 공연 조회 요청
- GET /performances/{performanceId}/reservations: 등록된 공연의 예약 현황 조회 요청
- POST /performances/{performanceId}/cancel: 공연 취소 요청

## 3. 예약

    예약 가능한 공연 조회, 원하는 공연 선택 예약, 예약 정보 확인, 수정
    취소 기능, 결제 및 결제 정보 확인 기능, 공연 예약 현황을 통한 수익 관리 기능

### 예약 API

- GET /reservations: 예약 가능한 공연 조회 요청
- POST /reservations: 공연 예약 요청
- GET /reservations/{reservationId}: 예약 정보 조회 요청
- PUT /reservations/{reservationId}: 예약 정보 수정 요청
- DELETE /reservations/{reservationId}: 예약 취소 요청
- GET /reservations/{reservationId}/payment: 결제 정보 확인 요청

## 4. 결제

    결제 정보 처리, 결제 상태 저장, 결제 취소 기능

### 결제 API

- POST /payments: 결제 요청
- PUT /payments/{paymentId}: 결제 상태 업데이트 요청
- DELETE /payments/{paymentId}: 결제 취소 요청

## 5. 이벤트

    예약, 결제, 취소에 대한 이벤트 발행 기능 제공, 이벤트를 수신하여 내부 데이터를 업데이트하는 기능

### 이벤트 API

- POST /events/reservations: 예약 이벤트 발행 요청
- POST /events/payments: 결제 이벤트 발행 요청
- POST /events/cancellations: 취소 이벤트 발행 요청
- POST /events/{serviceId}: 이벤트 수신 요청

## 6. 보안

    인증 및 인가 처리 기능

### 보안 API

- POST /auth: 인증 및 인가 처리 요청

## 7. 알림

    예약, 결제, 취소 알림을 전송하는 기능

### 알림 API

- POST /notifications/reservations: 예약 알림 전송 요청
- POST /notifications/payments: 결제 알림 전송 요청
- POST /notifications/cancellations: 취소 알림 전송 요청

## 8. 게이트웨이

    모든 클라이언트의 요청을 각 서비스로 전달하는 역할을 하는 서비스.

<br>

# **6. 주요 포인트**
