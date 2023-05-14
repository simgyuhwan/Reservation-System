package com.sim.reservationservice.api;

import static com.sim.reservationservice.factory.ReservationCommandConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.reservation.common.error.ErrorCode;
import com.sim.reservationservice.application.ReservationCommandService;
import com.sim.reservationservice.dto.request.ReservationDto;
import com.sim.reservationservice.dto.response.ReservationInfoDto;
import com.sim.reservationservice.error.PerformanceInfoNotFoundException;
import com.sim.reservationservice.error.ReservationControllerAdvice;
import com.sim.reservationservice.error.ReservationNotPossibleException;
import com.sim.reservationservice.error.SoldOutException;
import com.sim.reservationservice.factory.ReservationCommandDataFactory;

/**
 * ReservationControllerTest.java
 * 예약 api 관련 테스트
 *
 * @author sgh
 * @since 2023.04.25
 */
@ExtendWith(MockitoExtension.class)
class ReservationApiTest {
	private final String RESERVATION_BASE_URL = "/api/performances";
	private final String DEFAULT_RESERVATION_URL = RESERVATION_BASE_URL + "/1/schedules/1/reservations";

	private Gson gson;
	private MockMvc mockMvc;

	@InjectMocks
	private ReservationController reservationController;

	@Mock
	private ReservationCommandService reservationCommandService;

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
		when(reservationCommandService.createReservation(any(), any(), any())).thenReturn(createReservationInfoDto());

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationDto())));

		//then
		result.andExpect(jsonPath("$.name").value(NAME))
			.andExpect(jsonPath("$.phoneNum").value(PHONE_NUM))
			.andExpect(jsonPath("$.performanceName").value(PERFORMANCE_NAME));
	}

	@Test
	@DisplayName("공연 예약 API : 공연 예약 성공, 201 반환")
	void return200OnPerformanceReservationSuccess() throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(createReservationDto())))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("공연 예약 API : 공연에 속하지 않은 공연 신청 시, 오류 메시지 반환")
	void exceptionForApplicationForNonPerformance() throws Exception {
		//given
		when(reservationCommandService.createReservation(any(), any(), any())).thenThrow(
			NoSuchElementException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationDto())));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(
				jsonPath("$.message").value(ErrorCode.SCHEDULE_NOT_PART_OF_THE_PERFORMANCE_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("공연 예약 API : 예약 불가능한 공연일 시, 오류 메시지, 400 반환")
	void returnAnErrorMessageForUnReservablePerformance() throws Exception {
		//given
		when(reservationCommandService.createReservation(any(), any(), any())).thenThrow(
			ReservationNotPossibleException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationDto())));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.RESERVATION_NOT_POSSIBLE_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("공연 예약 API : 등록된 공연 정보가 없을 시, 오류 메시지, 404 반환")
	void errorMessageReturnedWhenThereIsNoRegisteredPerformanceInfo() throws Exception {
		//given
		when(reservationCommandService.createReservation(any(), any(), any())).thenThrow(
			PerformanceInfoNotFoundException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationDto())));

		//then
		result.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value(ErrorCode.NO_PERFORMANCE_INFORMATION_ERROR_MESSAGE.getMessage()));
	}

	@Test
	@DisplayName("공연 예약 API : 매진된 공연일 시, 오류 메시지, 400 반환")
	void returnSoldOutShowErrorMessage() throws Exception {
		//given
		when(reservationCommandService.createReservation(any(), any(), any())).thenThrow(SoldOutException.class);

		//when
		ResultActions result = mockMvc.perform(post(DEFAULT_RESERVATION_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createReservationDto())));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.PERFORMANCE_SOLD_OUT_ERROR_MESSAGE.getMessage()));

	}

	@ParameterizedTest
	@MethodSource("invalidEmailFormatValues")
	@DisplayName("공연 예약 API : 잘못된 이메일 형식일 시, 오류 메시지 반환 ")
	void invalidEmailFormat(ReservationDto reservationDto) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationDto)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].field").value("email"))
			.andExpect(jsonPath("$.errors[0].reason").value("이메일 형식에 맞지 않습니다."));
	}

	static Stream<Arguments> invalidEmailFormatValues() {
		return Stream.of(
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, "1")),
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, "test!naver.com")),
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, "------------------")),
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, "test@@@@@@@@")),
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, "@testnaver.com")),
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, "testnaver.com@"))
		);
	}

	@ParameterizedTest
	@MethodSource("invalidCellPhoneNumberFormatValues")
	@DisplayName("공연 예약 API : 잘못된 핸드폰 번호 형식일 시, 오류 메시지 반환")
	void invalidCellPhoneNumberFormat(ReservationDto reservationDto) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationDto)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].field").value("phoneNum"))
			.andExpect(jsonPath("$.errors[0].reason").value("핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx"));
	}

	static Stream<Arguments> invalidCellPhoneNumberFormatValues() {
		return Stream.of(
			Arguments.of(createReservationDto(USER_ID, NAME, "12345678", EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, "01030aaaaa", EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, "0-223-4048", EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, "02-22-4048", EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, "02-223-40488", EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, "-----------", EMAIL))
		);
	}

	@ParameterizedTest
	@MethodSource("reservedDTOWithNullOrBlankValues")
	@DisplayName("공연 예약 API :필수로 등록 되어야 할 값이 null 또는 공백일 시 오류 메시지 반환")
	void returnErrorMessageWhenMemberIDIsAbsent(ReservationDto reservationDto) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationDto)))
			.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PERFORMANCE_RESERVATION_INFORMATION.getMessage()));
	}

	@ParameterizedTest
	@MethodSource("reservedDTOWithNullOrBlankValues")
	@DisplayName("공연 예약 API : 필수로 등록 되어야 할 값이 null 또는 공백일 시 400 에러")
	void errorIsReturnedIfTheRequiredValueIsNotPresent(ReservationDto reservationDto) throws Exception {
		mockMvc.perform(post(DEFAULT_RESERVATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(reservationDto)))
			.andExpect(status().isBadRequest());
	}

	static Stream<Arguments> reservedDTOWithNullOrBlankValues() {
		return Stream.of(
			Arguments.of(createReservationDto(null, NAME, PHONE_NUM, EMAIL)),
			Arguments.of(createReservationDto(USER_ID, null, PHONE_NUM, EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, null, EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, null)),
			Arguments.of(createReservationDto(null, null, null, null)),
			Arguments.of(createReservationDto("", NAME, PHONE_NUM, EMAIL)),
			Arguments.of(createReservationDto(USER_ID, "", PHONE_NUM, EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, "", EMAIL)),
			Arguments.of(createReservationDto(USER_ID, NAME, PHONE_NUM, "")),
			Arguments.of(createReservationDto("", "", "", ""))
		);
	}

	private static ReservationDto createReservationDto() {
		return ReservationCommandDataFactory.createReservationDto();
	}

	private static ReservationDto createReservationDto(String userId, String name, String phoneNum, String email) {
		return ReservationCommandDataFactory.createReservationDto(userId, name, phoneNum, email);
	}

	private static ReservationInfoDto createReservationInfoDto() {
		return ReservationCommandDataFactory.createReservationInfoDto();
	}

}