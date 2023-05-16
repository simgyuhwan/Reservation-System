package com.sim.reservation.boot.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.sim.reservation.boot.dto.request.ReservationApplyRequest;
import com.sim.reservation.boot.dto.response.ReservationResultResponse;
import com.sim.reservation.boot.error.ErrorCode;
import com.sim.reservation.boot.error.ReservationControllerAdvice;
import com.sim.reservation.boot.factory.ReservationApplyRequestFactory;
import com.sim.reservation.boot.service.ReservationService;
import com.sim.reservation.data.reservation.error.PerformanceInfoNotFoundException;
import com.sim.reservation.data.reservation.error.ReservationNotPossibleException;
import com.sim.reservation.data.reservation.error.SoldOutException;

/**
 * ReservationControllerTest.java
 * 예약 api 관련 테스트
 *
 * @author sgh
 * @since 2023.04.25
 */
@ExtendWith(MockitoExtension.class)
class ReservationApiTest {
	private static final String USER_ID = "user1";
	private static final String NAME = "홍길동";
	private static final String PHONE_NUM = "010-8884-2133";
	private static final String EMAIL = "test@naver.com";
	private static final boolean IS_EMAIL_RECEIVE_DENIED = true;
	private static final boolean IS_SNS_RECEIVE_DENIED = true;

	private static final String PERFORMANCE_NAME = "나는 전설이다";
	private static final LocalDate PERFORMANCE_DATE = LocalDate.now();
	private static final LocalTime PERFORMANCE_TIME = LocalTime.now();
	private final String RESERVATION_BASE_URL = "/api/performances";
	private final String DEFAULT_RESERVATION_URL = RESERVATION_BASE_URL + "/1/schedules/1/reservations";

	private Gson gson;
	private MockMvc mockMvc;

	@InjectMocks
	private ReservationController reservationController;

	@Mock
	private ReservationService reservationService;

	@BeforeEach
	void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
			.setControllerAdvice(ReservationControllerAdvice.class).build();
	}

	@Test
	@DisplayName("공연 예약 API : 공연 예약 성공, 반환 값 확인")
	void performanceReservationSuccessReturnValueVerification() throws Exception {
		//given
		when(reservationService.applyReservation(any(), any(), any())).thenReturn(createApplySuccessResponse());

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationApplyRequest())));

		//then
		result.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value(ReservationResultResponse.ResultMessage.RESERVATION_APPLY_COMPLETE.getMessage()));
	}

	@Test
	@DisplayName("공연 예약 API : 공연 예약 성공, 201 반환")
	void return200OnPerformanceReservationSuccess() throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(createReservationApplyRequest())))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("공연 예약 API : 공연에 속하지 않은 공연 신청 시, 오류 메시지 반환")
	void exceptionForApplicationForNonPerformance() throws Exception {
		//given
		when(reservationService.applyReservation(any(), any(), any())).thenThrow(
			NoSuchElementException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationApplyRequest())));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(
				jsonPath("$.message").value(ErrorCode.SCHEDULE_NOT_PART_OF_THE_PERFORMANCE_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("공연 예약 API : 예약 불가능한 공연일 시, 오류 메시지, 400 반환")
	void returnAnErrorMessageForUnReservablePerformance() throws Exception {
		//given
		when(reservationService.applyReservation(any(), any(), any())).thenThrow(
			ReservationNotPossibleException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationApplyRequest())));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.RESERVATION_NOT_POSSIBLE_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("공연 예약 API : 등록된 공연 정보가 없을 시, 오류 메시지, 404 반환")
	void errorMessageReturnedWhenThereIsNoRegisteredPerformanceInfo() throws Exception {
		//given
		when(reservationService.applyReservation(any(), any(), any())).thenThrow(
			PerformanceInfoNotFoundException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationApplyRequest())));

		//then
		result.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value(ErrorCode.NO_PERFORMANCE_INFORMATION_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("공연 예약 API : 매진된 공연일 시, 오류 메시지, 400 반환")
	void returnSoldOutShowErrorMessage() throws Exception {
		//given
		when(reservationService.applyReservation(any(), any(), any())).thenThrow(SoldOutException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationApplyRequest())));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.PERFORMANCE_SOLD_OUT_ERROR_MESSAGE.getMessage()));

	}

	@ParameterizedTest
	@MethodSource("invalidEmailFormatValues")
	@DisplayName("공연 예약 API : 잘못된 이메일 형식일 시, 오류 메시지 반환 ")
	void invalidEmailFormat(ReservationApplyRequest reservationApplyRequest) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApplyRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].field").value("email"))
			.andExpect(jsonPath("$.errors[0].reason").value("이메일 형식에 맞지 않습니다."));
	}

	static Stream<Arguments> invalidEmailFormatValues() {
		return Stream.of(
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, "1")),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, "test!naver.com")),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, "------------------")),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, "test@@@@@@@@")),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, "@testnaver.com")),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, "testnaver.com@"))
		);
	}

	@ParameterizedTest
	@MethodSource("invalidCellPhoneNumberFormatValues")
	@DisplayName("공연 예약 API : 잘못된 핸드폰 번호 형식일 시, 오류 메시지 반환")
	void invalidCellPhoneNumberFormat(ReservationApplyRequest reservationApplyRequest) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApplyRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].field").value("phoneNum"))
			.andExpect(jsonPath("$.errors[0].reason").value("핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx"));
	}

	static Stream<Arguments> invalidCellPhoneNumberFormatValues() {
		return Stream.of(
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, "12345678", EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, "01030aaaaa", EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, "0-223-4048", EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, "02-22-4048", EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, "02-223-40488", EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, "-----------", EMAIL))
		);
	}

	@ParameterizedTest
	@MethodSource("reservedDTOWithNullOrBlankValues")
	@DisplayName("공연 예약 API :필수로 등록 되어야 할 값이 null 또는 공백일 시 오류 메시지 반환")
	void returnErrorMessageWhenMemberIDIsAbsent(ReservationApplyRequest reservationApplyRequest) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApplyRequest)))
			.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PERFORMANCE_RESERVATION_INFORMATION.getMessage()));
	}

	@ParameterizedTest
	@MethodSource("reservedDTOWithNullOrBlankValues")
	@DisplayName("공연 예약 API : 필수로 등록 되어야 할 값이 null 또는 공백일 시 400 에러")
	void errorIsReturnedIfTheRequiredValueIsNotPresent(ReservationApplyRequest reservationApplyRequest) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApplyRequest)))
			.andExpect(status().isBadRequest());
	}

	static Stream<Arguments> reservedDTOWithNullOrBlankValues() {
		return Stream.of(
			Arguments.of(createReservationApplyRequest(null, NAME, PHONE_NUM, EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, null, PHONE_NUM, EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, null, EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, null)),
			Arguments.of(createReservationApplyRequest(null, null, null, null)),
			Arguments.of(createReservationApplyRequest("", NAME, PHONE_NUM, EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, "", PHONE_NUM, EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, "", EMAIL)),
			Arguments.of(createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, "")),
			Arguments.of(createReservationApplyRequest("", "", "", ""))
		);
	}
	
	private static ReservationApplyRequest createReservationApplyRequest() {
		return ReservationApplyRequestFactory.createReservationApplyRequest(USER_ID, NAME, PHONE_NUM, EMAIL, IS_EMAIL_RECEIVE_DENIED, IS_SNS_RECEIVE_DENIED);
	}

	private static ReservationApplyRequest createReservationApplyRequest(String userId, String name, String phoneNum, String email) {
		return ReservationApplyRequestFactory.createReservationApplyRequest(userId, name, phoneNum, email, IS_EMAIL_RECEIVE_DENIED, IS_SNS_RECEIVE_DENIED);
	}

	private ReservationResultResponse createApplySuccessResponse() {
		return ReservationResultResponse.applyComplete(1L);
	}
}