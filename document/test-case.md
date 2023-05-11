# 기능별 테스트 케이스

## 목차

### [▶ 회원](#▶-회원)

#### [1. 회원 가입 API](#1-회원-가입-apipost-apisignup)

#### [2. 로그인 아이디로 회원 조회 API](#2-로그인-아이디로-회원-조회-apiget-apimembersuseriduserid)

#### [3. 회원 수정 API](#3-회원-수정-apiput-apimembersuserid)

#### [4. 회원이 등록한 공연 조회 AP](#4-회원이-등록한-공연-조회-api-get-apimembersuseridperformances)

#### [5. memberId로 회원 상세 조회 API](#5-memberid로-회원-상세-조회-api-get-apimembersmemberid)

### [▶ 공연](#▶공연)

#### [1. 공연 등록 API](#1-공연-등록-apipost-apiperformances)

#### [2. 공연 수정 API](#2-공연-수정-apiput-apiperformancesperformanceid)

#### [3. 회원ID로 등록된 공연 조회 API](#3-회원id로-등록된-공연-조회-apiget-apiperformancesuseriduserid)

#### [4. 공연ID 공연 상세 조회 API](#4-공연id-공연-상세-조회-apiget-apiperformancesperformanceid)

### [▶ 예약](#▶-예약)

#### [1. 공연 예약 현황 조회 API](#1-공연-예약-현황-조회-api-get-reservation-serviceapiperformancesavailable)

#### [2. 공연 예약 신청 API](#2-공연-예약-신청-api-post-reservation-serviceapiperformancesperformanceidschedulesscheduleidreservations)

---

<br>

# ▶ 회원

- memberId 는 Entity의 식별 값, userId 는 로그인 Id

<br>

## 1. 회원 가입 API(POST /api/signup)

### 요청 시 필수 입력값

| 매개변수 이름 | 설명        |
| ------------- | ----------- |
| \*userId      | 회원 ID     |
| \*username    | 이름        |
| \*password    | 비밀번호    |
| \*phoneNum    | 핸드폰 번호 |
| \*address     | 주소        |

### 검증

1. 회원 가입 성공, 201 상태 코드 반환
2. 필수 값 미포함 시, 400 상태 코드 반환
3. 각 필드 값 유효성 검증 실패 시, 400 상태 코드 반환
4. 각 필드 값 유효성 검증 실패 시, 필드별 오류 메시지 반환

---

<br>

## 2. 로그인 아이디로 회원 조회 API(GET /api/members?userId={userId})

### 요청 시 필수 입력값

| 파라미터 | 설명    |
| -------- | ------- |
| \*userId | 회원 ID |

### 검증

1. 조회 성공, 201 상태 코드 반환
2. 조회 성공, 응답 값 확인
3. 일치하는 로그인 ID 없음, 오류 메시지 반환
4. 일치하는 로그인 ID 없음, 400 상태 코드 반환

---

<br>

## 3. 회원 수정 API(PUT /api/members/{userId})

### 요청 시 필수 입력값

| 매개변수 이름 | 설명        |
| ------------- | ----------- |
| \*userId      | 회원 ID     |
| \*username    | 이름        |
| \*phoneNum    | 핸드폰 번호 |
| \*address     | 주소        |

### 검증

1. 존재하지 않는 로그인 ID 요청 시, 400 상태 코드 반환
2. 필수 값 미포함 시, 400 상태 코드 반환
3. 각 필드 값 유효성 검증 실패 시, 400 상태 코드 반환
4. 각 필드 값 유효성 검증 실패 시, 필드별 오류 메시지 반환
5. 수정 성공, 200 상태 코드 반환

---

<br>

## 4. 회원이 등록한 공연 조회 API( GET /api/members/{userId}/performances)

### 요청 시 필수 입력값

| 파라미터 | 설명    |
| -------- | ------- |
| memberId | 회원 ID |

### 검증

1. 등록된 공연 정보가 없으면 빈 리스트로 반환
2. 유효하지 않은 회원 ID, 400 상태 코드 반환
3. 유효하지 않은 회원 ID, 오류 메시지 확인
4. 공연 서비스의 요청 장애 시, 빈 리스트 반환
5. 등록된 공연 정보 조회 성공, 200 상태 코드 반환

---

<br>

## 5. memberId로 회원 상세 조회 API( GET /api/members/{memberId})

| 파라미터 | 설명    |
| -------- | ------- |
| memberId | 회원 ID |

### 검증

1. memberId로 등록된 회원이 없을 시, 400 상태 코드 반환
2. memberId로 등록된 회원이 없을 시, 오류 메시지 확인
3. 등록된 회원 정보 조회 성공, 200 상태 코드 반환
4. 등록된 회원 정보 조회 성공, 응답 값 확인

---

<br>

# ▶ 공연

<br>

## 1. 공연 등록 API(POST /api/performances)

### 요청 시 필수 입력값

| 매개변수 이름        | 설명           |
| -------------------- | -------------- |
| performanceId        | 공연 ID        |
| userId               | 회원 ID        |
| performanceName      | 공연 이름      |
| performanceStartDate | 공연 시작일    |
| performanceEndDate   | 공연 종료일    |
| performanceType      | 공연 타입      |
| audienceCount        | 관객 수        |
| price                | 가격           |
| contactPhoneNum      | 담당자 번호    |
| contactPersonName    | 담당자 이름    |
| performanceInfo      | 공연 정보      |
| performancePlace     | 공연 장소      |
| performanceTimes     | 공연 시작 시간 |

### 검증

1. 모든 값이 제공되었는지 확인
2. 종료일이 시작일보다 먼저인지 확인
3. 공연 시작일이 현재 시점보다 이전인지 확인
4. 수용 관객 수가 1 이상인지 확인
5. 담당자 번호가 유효한 값인지 확인
6. 공연 소개 필드의 최대 길이를 초과한 값이 제공되었을 때, 적절한 오류 메시지 반환 확인

---

<br>

## 2. 공연 수정 API(PUT /api/performances/{performanceId})

### 요청 시 필수 입력값

| 매개변수 이름        | 설명           |
| -------------------- | -------------- |
| performanceId        | 공연 ID        |
| userId               | 회원 ID        |
| performanceName      | 공연 이름      |
| performanceStartDate | 공연 시작일    |
| performanceEndDate   | 공연 종료일    |
| performanceType      | 공연 타입      |
| audienceCount        | 관객 수        |
| price                | 가격           |
| contactPhoneNum      | 담당자 번호    |
| contactPersonName    | 담당자 이름    |
| performanceInfo      | 공연 정보      |
| performancePlace     | 공연 장소      |
| performanceTimes     | 공연 시작 시간 |

### 검증

1. 존재하지 않는 공연 조회시, 오류메시지 반환
2. 종료일이 시작일보다 먼저인지 확인
3. 공연 시작일이 현재 시점보다 이전인지 확인

---

<br>

## 3. 회원ID로 등록된 공연 조회 API(GET /api/performances?userId={userId})

### 요청 시 필수 입력값(Query Param)

| 매개변수 이름 | 설명    |
| ------------- | ------- |
| userId        | 회원 ID |

### 검증

1. 회원 ID로 등록된 공연이 없을 시, 오류 메시지 반환
2. 회원이 등록한 공연이 1개 이상 있는 경우, 공연 리스트 반환
3. 등록일 기준으로 최신 순으로 반환 확인

---

<br>

## 4. 공연ID 공연 상세 조회 API(GET /api/performances/{performanceId}

### 요청 시 필수 입력값

| 매개변수 이름 | 설명    |
| ------------- | ------- |
| performanceId | 공연 ID |

1. 공연 ID로 등록된 공연이 없을 시, 오류 메시지 반환
2. 공연 ID로 등록된 공연이 없을 시, 400 코드 반
3. 공연 ID로 공연 상세 정보 반

---

<br>

## 5. 공연 등록 신청중인 공연 정보 조회 API(GET /api/performances/{performanceId}/status/pending

### 요청 시 필수 입력값

| 매개변수 | 설명 |
| --- | --- |
| performanceId | 공연 ID |
1. 공연 등록 중인 공연 정보가 없을 시, 400 상태 코드 반환
2. 공연 등록 중인 공연 정보가 없을 시 에러 메시지 반환
3. 공연 등록 중인 공연 정보 조회 성공, 200 상태 코드 반환

<br>

---

<br>

## 6. 공연 등록 상태 조회 API(GET /api/performances/{performanceId}/status

### 요청 시 필수 입력값

| 매개변수 | 설명 |
| --- | --- |
| performanceId | 공연 ID |
1. 공연 등록 중인 공연 정보가 없을 시, 400 상태 코드 반환
2. 공연 등록 중인 공연 정보가 없을 시, 에러 메시지 반환
3. 공연 등록 중인 공연 정보 조회 성공, 200 상태 코드 반환
4. 공연 등록 중인 공연 정보 조회 성공, 상태 값 확인
5. 공연 등록 중인 공연 정보 조회 성공, 등록 대기 상태 메시지 확인
6. 공연 등록 중인 공연 정보 조회 성공, 등록 완료 메시지 확인
7. 공연 등록 중인 공연 정보 조회 성공, 등록 실패 메시지 확인

---

<br>

# ▶ 예약

<br>

## 1. 공연 예약 현황 조회 API (_GET /reservation-service/api/performances/available_)

### 요청 시 필수 입력값

| 파라미터    | 설명           |
| ----------- | -------------- |
| startDate   | 공연 시작 날짜 |
| endDate     | 공연 종료 날짜 |
| startTime   | 공연 시작 시간 |
| endTime(X)  | 공연 종료 시간 |
| name        | 공연 이름      |
| type        | 공연 타입      |
| place       | 공연 장소      |
| 페이지 정보 | Pageable       |

**요청 예시**

```java
reservation-service/api/performances/available
	?startDate=2024-01-01&endDate=2025-01-01&name=바람과 함께 사라지다
	&startTime=11:00&endTime=13:00&type=MUSICAL&place=홍대1극장
```

### 검증

1. 모든 조건이 주어젔을 때, 조건에 맞는 공연 정보 반환 확인
2. 조건 중 일부만 주어졌을 때, 주어징 조건에 맞는 공연 정보 반환 확인
3. 조건이 없을 때, 기본 값으로 공연 정보 반환 확인
4. 잘못된 형식의 조건이 주어졌을 때, 오류 메시지 반환 확인
5. 조건에 맞는 공연 정보가 없을 때, 빈 리스트 반환 확인
6. 날짜, 시간 데이터의 시작과 끝이 잘못되었을 시, 오류 메시지 반환.

<br>

## 2. 공연 예약 신청 API (POST _/reservation-service/api/performances/{performanceId}/schedules/{scheduleId}/reservations_

| 매개변수 이름        | 설명             |
| -------------------- | ---------------- |
| userId\*             | 회원 ID          |
| isEmailReceiveDenied | 이메일 수신 거부 |
| isSnsReceiveDenied   | SNS 수신 거부    |
| name\*               | 예약자 이름      |
| phoneNum\*           | 예약자 번호      |
| email\*              | 예약자 이메일    |

### 검증

1. 필수로 등록되어야 할 값이 null 또는 공백일시 오류 메시지 반환
2. 잘못된 이메일 형식일 시, 오류 메시지 반환
3. 잘못된 핸드폰 번호 형식일 시, 오류 메시지 반환
4. 좌석이 매진되어 예약 불가능일 시, 오류 메시지 반환
5. 등록 공연 정보가 없을 시, 오류 메시지 반환
6. 예약 불가능한 공연일 시, 오류 메시지 반환
7. 공연 예약 성공, 201 반환
8. 공연에 속하지 않은 공연 신청 시, 오류 메시지 반환
9. 하나 남은 좌석 예약 신청 후 매진 확
