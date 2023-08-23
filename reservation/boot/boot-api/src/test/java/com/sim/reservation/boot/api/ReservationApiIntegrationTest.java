package com.sim.reservation.boot.api;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sim.reservation.boot.dto.request.ReservationApplyRequest;
import com.sim.reservation.boot.dto.response.ReservationResultResponse;
import com.sim.reservation.boot.error.ErrorCode;
import com.sim.reservation.boot.type.ResultMessage;
import com.sim.reservation.data.reservation.error.PerformanceInfoNotFoundException;
import com.sim.reservation.data.reservation.error.ReservationNotPossibleException;
import com.sim.reservation.data.reservation.error.SoldOutException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

/**
 * ReservationControllerTest.java
 * 예약 api 관련 테스트
 *
 * @author sgh
 * @since 2023.04.25
 */
class ReservationApiIntegrationTest extends ControllerTestSupport{
	private static final String USER_ID = "user1";
	private static final String NAME = "홍길동";
	private static final String PHONE_NUM = "010-8884-2133";
	private static final String EMAIL = "test@naver.com";
	private final String RESERVATION_BASE_URL = "/api/performances";
	private final String DEFAULT_RESERVATION_URL = RESERVATION_BASE_URL + "/1/schedules/1/reservations";

	@Test
	@DisplayName("공연 예약 신청을 할 수 있다.")
	void performanceReservationSuccessReturnValueVerification() throws Exception {
		// given
		ReservationResultResponse response = ReservationResultResponse.applyComplete(
			1L);
		ReservationApplyRequest reservationApply = createReservationApplyRequest();
		given(reservationService.applyReservation(any(), any(), any())).willReturn(response);

		// when, then
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApply)))
			.andExpect(
				jsonPath("$.message").value(ResultMessage.RESERVATION_APPLY_COMPLETE.getMessage()))
			.andExpect(jsonPath("$.reservationId").value(response.getReservationId()));
	}

	@Test
	@DisplayName("공연 예약 신청시 201 코드를 반환받는다.")
	void return201OnPerformanceReservationSuccess() throws Exception {
		// given
		ReservationApplyRequest request = createReservationApplyRequest();

		// when, then
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(request)))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("등록되지 않는 공연 시간으로 예약하면 실패하고 예외 메시지를 받는다.")
	void exceptionForApplicationForNonPerformance() throws Exception {
		// given
		given(reservationService.applyReservation(any(), any(), any())).willThrow(
			NoSuchElementException.class);
		ReservationApplyRequest request = createReservationApplyRequest();

		// when, then
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)))
			.andExpect(status().isBadRequest())
			.andExpect(
				jsonPath("$.message").value(ErrorCode.SCHEDULE_NOT_PART_OF_THE_PERFORMANCE_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("예약 불가능한 공연을 예약하면 예외 메시지를 반환받는다.")
	void returnAnErrorMessageForUnReservablePerformance() throws Exception {
		// given
		given(reservationService.applyReservation(any(), any(), any())).willThrow(
			ReservationNotPossibleException.class);
		ReservationApplyRequest request = createReservationApplyRequest();

		// when, then
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.RESERVATION_NOT_POSSIBLE_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("등록되지 않은 공연 정보로 조회하면 예외 메시지를 반환받는다.")
	void errorMessageReturnedWhenThereIsNoRegisteredPerformanceInfo() throws Exception {
		// given
		given(reservationService.applyReservation(any(), any(), any())).willThrow(
			PerformanceInfoNotFoundException.class);
		ReservationApplyRequest request = createReservationApplyRequest();

		// when, then
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value(ErrorCode.NO_PERFORMANCE_INFORMATION_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("매진된 공연을 예약하면 예외 메시지를 반환받는다.")
	void returnSoldOutShowErrorMessage() throws Exception {
		// given
		given(reservationService.applyReservation(any(), any(), any())).willThrow(SoldOutException.class);
		ReservationApplyRequest request = createReservationApplyRequest();

		// when, then
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.PERFORMANCE_SOLD_OUT_ERROR_MESSAGE.getMessage()));

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
	@MethodSource("invalidEmailFormatValues")
	@DisplayName("잘못된 이메일 형식을 사용하면 예외 메시지를 반환받는다.")
	void invalidEmailFormat(ReservationApplyRequest reservationApplyRequest) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApplyRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].field").value("email"))
			.andExpect(jsonPath("$.errors[0].reason").value("이메일 형식에 맞지 않습니다."));
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
	@MethodSource("invalidCellPhoneNumberFormatValues")
	@DisplayName("잘못된 핸드폰 번호 양식을 사용하면 예외 메시지를 반환받는다.")
	void invalidCellPhoneNumberFormat(ReservationApplyRequest reservationApplyRequest) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApplyRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].field").value("phoneNum"))
			.andExpect(jsonPath("$.errors[0].reason").value("핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx"));
	}

	@ParameterizedTest
	@MethodSource("reservedDTOWithNullOrBlankValues")
	@DisplayName("공연 예약시 필수로 등록된 값을 넣지 않으면 예외 메시지를 반환한다.")
	void returnErrorMessageWhenMemberIDIsAbsent(ReservationApplyRequest reservationApplyRequest) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationApplyRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PERFORMANCE_RESERVATION_INFORMATION.getMessage()));
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
		return ReservationApplyRequest.builder()
			.userId("user")
			.name("홍길동")
			.email("test@naver.com")
			.phoneNum("010-8888-2222")
			.isEmailReceiveDenied(true)
			.isSmsReceiveDenied(true)
			.build();
	}


	private static ReservationApplyRequest createReservationApplyRequest(String userId, String name, String phoneNum, String email) {
		return ReservationApplyRequest.builder()
			.userId(userId)
			.name(name)
			.phoneNum(phoneNum)
			.email(email)
			.isEmailReceiveDenied(true)
			.isSmsReceiveDenied(true)
			.build();
	}

}