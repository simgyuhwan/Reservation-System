# **API 문서**

각 api의 보다 더 자세한 내용은 서비스의 **/swagger** 를 통해서 확인이 가능하다.

---

## 목차

[회원 API](#회원-api)

[공연 API](#공연-api)

[예약 API]()

[결제 API]()

---

## 회원 API

---

| Index |  Feature  | Method | End Point             | query string | Request Body                                                                                                                          | Response Body                                                                                                         |
| ----- | :-------: | :----: | --------------------- | ------------ | ------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| 1     | 회원 가입 |  POST  | /api/signup           |              | {<br>"userId": "string",<br>"username": "string",<br>"password":"string",<br>"phoneNum": "010-496-4055",<br> "address": "string"<br>} | code : 201                                                                                                            |
| 2.    | 회원 조회 |  GET   | /api/members/{userId} |              |                                                                                                                                       | code : 200 <br>{<br>"userId": "string",<br>"phoneNum": "string",<br>"username": "string","address": "string"<br>}     |
| 3.    | 회원 수정 |  PUT   | /api/members/{userId} |              | {<br>"userId": "string",<br>"phoneNum": "95-464-5765",<br>"username": "string"<br>"address": "string"<br>}                            | code : 200 <br>{<br>"userId": "string",<br>"phoneNum": "string",<br>"username": "string",<br>"address": "string"<br>} |

---

<br>

## 공연 API

| Index |           Feature            | Method | End Point                         | query string | Request Body                                                                                                                                                                                                                                                                                                                                                                                                                                       | Response Body                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ----- | :--------------------------: | :----: | --------------------------------- | ------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| 1     |          공연 등록           |  POST  | /api/performances                 |              | {<br>"performanceId": 0,<br>"userId": "test",<br>"performanceName": "오페라의 유령",<br>"performanceStartDate": "2024-01-01",<br>"performanceEndDate": "2024-01-01",<br>"performanceType": "THEATER",<br>"audienceCount": 100,<br>"price": 10000,<br>"contactPhoneNum": "010-1234-1234",<br>"contactPersonName": "홍길동",<br>"performanceInfo": "끝까지 간다....",<br>"performancePlace": "홍대 시네마",<br>"performanceTimes": "[15:00]"<br>}    | code : 201                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| 2.    | 회원이 등록한 공연 전체 조회 |  GET   | /api/performances/{userId}        |              |                                                                                                                                                                                                                                                                                                                                                                                                                                                    | code : 200 <br>[<br>{<br>"performanceId": 0,<br>"userId": "test",<br>"performanceName": "오페라의 유령",<br>"performanceStartDate": "2024-01-01",<br>"performanceEndDate": "2024-01-01",<br>"performanceType": "THEATER",<br>"audienceCount": 100,<br>"price": 10000,<br>"contactPhoneNum": "010-1234-1234",<br>"contactPersonName": "홍길동",<br>"performanceInfo": "끝까지 간다....",<br>"performancePlace": "홍대 시네마",<br>"performanceTimes": "[15:00]"<br>}<br>] |
| 3.    |          공연 수정           |  PUT   | /api/performances/{performanceId} |              | <br>{"performanceId": 0,<br>"userId": "test",<br>"performanceName":<br>"오페라의 유령",<br>"performanceStartDate": "2024-01-01",<br>"performanceEndDate": "2024-01-01",<br>"performanceType": "THEATER",<br>"audienceCount": 100,<br>"price": 10000,<br>"contactPhoneNum": "010-1234-1234",<br>"contactPersonName": "홍길동",<br>"performanceInfo": "끝까지 간다....",<br>"performancePlace": "홍대 시네마",<br>"performanceTimes": "[15:00]"<br>} | code : 200<br>{<br>"performanceId": 0,<br>"userId": "test",<br>"performanceName": "오페라의 유령",<br>"performanceStartDate": "2024-01-01",<br>"performanceEndDate": "2024-01-01",<br>"performanceType": "THEATER",<br>"audienceCount": 100,<br>"price": 10000,<br>"contactPhoneNum": "010-1234-1234",<br>"contactPersonName": "홍길동",<br>"performanceInfo": "끝까지 간다....",<br>"performancePlace": "홍대 시네마",<br>"performanceTimes": "[15:00]"<br>}            |

---

# Before

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
