# **API 문서**

API의 간단한 목록과 각 상세 정보들이 기록된 문서이다.  
각 api의 자세한 내용은 서비스의 **/gateway-server/swagger** 를 통해서 확인이 가능하다.

---

<br>

## API 목록

<br>

### **1. 회원 서비스**

    회원가입, 로그인, 로그아웃, 회원정보 수정, 조회, 회원 탈퇴

### **API**

- POST /member-service/api/signup: 회원가입 요청
- GET /member-service/api/members?userId={userId}: 로그인 ID로 사용자 정보 조회
- PUT /member-service/api/members/{userId}: 사용자 정보 수정
- GET /member-service/api/members/{memberId}/performances: 회원이 등록한 공연 조회
- GET /member-service/api/members/{memberId} : memberId로 사용자 정보 조회

- POST /login: 로그인 요청
- POST /logout: 로그아웃 요청
- DELETE /members/{userId}: 사용자 회원 탈퇴 요청

---

<br>

### **2. 공연 서비스**

    공연 정보 등록, 수정, 공연 일정 및 가격 설정, 등록된 공연 조회, 등록한 공연 상태 조회, 공연 등록 신청중인 공연 정보 조회
    공연 예약 현황 조회, 공연 취소 또는 변경 알림 발송 기능

### **API**

- POST /performance-service/api/performances: 공연 정보 등록 요청
- GET /performance-service/api/performances?userId={userId}: 회원이 등록한 공연 전체 조회
- GET /performance-service/api/performances/{performanceId}: performanceId를 통한 공연 조회
- PUT /performance-service/api/performances/{performanceId}: 공연 정보 수정 요청
- GET /performance-service/api/performances/{performanceId}/status/pending: 공연 등록 신청중인 공연 정보 조회
- GET /performance-service/api/performances/{performanceId}/status: 등록한 공연 상태 조회
- GET /performances: 등록된 공연 조회 요청
- GET /performances/{performanceId}/reservations: 등록된 공연의 예약 현황 조회 요청
- POST /performances/{performanceId}/cancel: 공연 취소 요청

---

<br>

### **3. 예약 서비스**

    예약 가능한 공연 조회, 원하는 공연 선택 예약, 예약 정보 확인, 수정, 취소 기능

### **API**

- GET /reservation-service/api/performances/available : 공연 예약 현황 조회
- GET /reservation-sersvice/api/reservations: 예약 가능한 공연 조회 요청
- POST /reservation-service/api/reservations: 공연 예약 요청
- GET reservation-service/api/reservations/{reservationId}: 예약 정보 조회 요청
- PUT reservation-service/api/reservations/{reservationId}: 예약 정보 수정 요청
- DELETE reservation-service/api/reservations/{reservationId}: 예약 취소 요청

---

<br>

## **4. 이벤트 서비스**

    예약, 결제, 취소에 대한 이벤트 발행 기능 제공, 이벤트를 수신하여 내부 데이터를 업데이트하는 기능

### **API**
 
 - kafka 통신

---

<br>

## 5. **인증 서비스**

    인증 및 인가 처리 기능

### **API**

- POST /auth: 인증 및 인가 처리 요청

---

<br>

## **6. 알림 서비스**

    예약 취소, 신청 알림을 전송하는 기능

### **API**

- kafka 통신

---

<br>

## API 상세 명세

<br>

### 회원 API

---

| Index |          Feature           | Method | End Point                                           | query string | Request Body                                                                                                                          |
| ----- | :------------------------: | :----: | --------------------------------------------------- | ------------ | ------------------------------------------------------------------------------------------------------------------------------------- |
| 1     |         회원 가입          |  POST  | /member-service/api/signup                          |              | {<br>"userId": "string",<br>"username": "string",<br>"password":"string",<br>"phoneNum": "010-496-4055",<br> "address": "string"<br>} |
| 2.    | 로그인 ID로 회원 상세 조회 |  GET   | /member-service/api/members?userId={userId}         |              |
| 3.    |         회원 수정          |  PUT   | /member-service/api/members/{userId}                |              | {<br>"userId": "string",<br>"phoneNum": "95-464-5765",<br>"username": "string"<br>"address": "string"<br>}                            |
| 4.    |  회원이 등록한 공연 조회   |  GET   | /member-service/api/members/{memberId}/performances |              |

---

<br>

### 공연 API

| Index | Feature                             | Method | End Point                                             | query string | Request Body                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| ----- | ----------------------------------- | ------ | ----------------------------------------------------- | ------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| 1     | 공연 등록                           | POST   | /performance-service/api/performances                 |              | {<br>"performanceId": 0,<br>"userId": "test",<br>"performanceName": "오페라의 유령",<br>"performanceStartDate": "2024-01-01",<br>"performanceEndDate": "2024-01-01",<br>"performanceType": "THEATER",<br>"audienceCount": 100,<br>"price": 10000,<br>"contactPhoneNum": "010-1234-1234",<br>"contactPersonName": "홍길동",<br>"performanceInfo": "끝까지 간다....",<br>"performancePlace": "홍대 시네마",<br>"performanceTimes": "[15:00]"<br>}        |
| 2.    | 회원 ID를 사용하여 등록된 공연 조회 | GET    | /performance-service/api/performances                 | memberId=1   |
| 3.    | 공연 수정                           | PUT    | /performance-service/api/performances/{performanceId} |              | <br>{<br>"performanceId": 0,<br>"userId": "test",<br>"performanceName":<br>"오페라의 유령",<br>"performanceStartDate": "2024-01-01",<br>"performanceEndDate": "2024-01-01",<br>"performanceType": "THEATER",<br>"audienceCount": 100,<br>"price": 10000,<br>"contactPhoneNum": "010-1234-1234",<br>"contactPersonName": "홍길동",<br>"performanceInfo": "끝까지 간다....",<br>"performancePlace": "홍대 시네마",<br>"performanceTimes": "[15:00]"<br>} |
| 4.    | 공연 상세 조회                      | GET    | /performance-service/api/performances/{performanceId} |              |
| 5.    | 등록중인 공연 상세 조회                     | GET    | /performance-service/api/performances/{performanceId}/status/pending |              |
| 6.    | 등록한 공연 상태 조회                   | GET    | /performance-service/api/performances/{performanceId}/status |              |


<br>

### 예약 API

| Index | Feature             | Method | End Point                                                                                           | query string                                                                                                                                     | Request Body                                                                                                                                               |
| ----- | ------------------- | ------ | --------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1     | 공연 예약 현황 조회 | GET    | /reservation-service/api/performances/available                                                     | startDate=2023-06-01&endDate=2025-01-01&name=바람과 함께 사라지다&startTime=11:00&endTime=13:00&type=MUSICAL&place=신촌 제 2 극장&page=1&size=15 |                                                                                                                                                            |
| 2     | 공연 예약 신청      | POST   | /reservation-service/api/performances/available/{performanceId}/schedules/{scheduleId}/reservations |                                                                                                                                                  | {“userId”: “test”, “name”:” 홍길동”,“phoneNum”: ”010-8888-9999”,“email” : “test@email.com”, “isEmailReceivedDenied” : “true”,“isSnsReceiveDenied” : “true} |
| 3  | 공연 예약 정보 조회 요청   | GET   | /reservation-service/api/reservations/{reservationId} |
|4  | 공연 예약 신청 취소   | DELETE | /reservation-service/api/reservations/{reservationId} |  