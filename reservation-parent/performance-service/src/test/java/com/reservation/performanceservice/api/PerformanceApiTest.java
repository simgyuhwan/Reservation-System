package com.reservation.performanceservice.api;

import static com.reservation.performanceservice.factory.PerformanceTestDataFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Set;
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
import com.reservation.performanceservice.application.PerformanceQueryService;
import com.reservation.performanceservice.dto.request.PerformanceDto;
import com.reservation.performanceservice.error.InvalidPerformanceDateException;
import com.reservation.performanceservice.error.PerformanceControllerAdvice;
import com.reservation.performanceservice.factory.PerformanceTestDataFactory;

@ExtendWith(MockitoExtension.class)
public class PerformanceApiTest {
	private static final String PERFORMANCE_API_URL = "/api/performances";
	private static final String INVALID_REGISTER_VALUE_ERROR_MESSAGE = "공연 등록 값이 올바르지 않습니다.";
	private static final String INVALID_CONTACT_NUMBER_ERROR_MESSAGE = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx";
	private static final String PERFORMANCE_INFO_LENGTH_EXCEEDED_ERROR_MESSAGE = "공연 정보는 최대 255자입니다.";
	private static final String INCORRECT_PERFORMANCE_DATE_FORMAT_ERROR_MESSAGE = "공연 날짜 형식이 잘못되었습니다. ex) '2024-01-01'";
	private static final String INCORRECT_PERFORMANCE_TIME_ERROR_MESSAGE = "공연 시간 형식이 잘못되었습니다. ex) '15:45'";
	private static final Integer PERFORMANCE_MAXIMUM_COUNT = 255;
	private static final String MIN_AUDIENCE_ERROR_MESSAGE = "관객 수는 반드시 10명 이상이어야 합니다.";

	private MockMvc mockMvc;
	private Gson gson;

	@InjectMocks
	private PerformanceController performanceController;

	@Mock
	private PerformanceQueryService performanceQueryService;

	@BeforeEach()
	void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(performanceController)
			.setControllerAdvice(PerformanceControllerAdvice.class)
			.build();
	}

	@Test
	@DisplayName("공연 등록 API : 공연 등록 성공 - 201 상태코드 반환")
	void performanceRegisterSuccessTest() throws Exception{
		mockMvc.perform(post(PERFORMANCE_API_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(PerformanceTestDataFactory.createPerformanceRegisterDto())))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("공연 등록 API : 종료일이 시작일보다 먼저면 오류 메시지 반환")
	void incorrectRegistrationScheduleErrorReturned() throws Exception {
		//when
		willThrow(InvalidPerformanceDateException.class)
			.given(performanceQueryService)
			.createPerformance(any());

		ResultActions result = mockMvc.perform(post(PERFORMANCE_API_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(createPerformanceRegisterDtoWithInvalidDate())));

		// then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PERFORMANCE_DATE_VALUE.getMessage()));
	}

	@Test
	@DisplayName("공연 등록 API : 수용 관객, 최소 값 이상이 아니면 오류 메시지 반환")
	void minimumAudienceException() throws Exception {
		//given
		PerformanceDto registerDto = createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE,
			PERFORMANCE_END_DATE,
			PERFORMANCE_TIMES, PERFORMANCE_TYPE, 1,
			PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

		//when,then
		mockMvc.perform(post(PERFORMANCE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(registerDto)))
			.andExpect(jsonPath("$.errors[0].field").value("audienceCount"))
			.andExpect(jsonPath("$.errors[0].reason").value(MIN_AUDIENCE_ERROR_MESSAGE));
	}

	@Test
	@DisplayName("공연 등록 API : 잘못된 담당자 핸드폰 번호 입력시, 오류 메시지 반환")
	void wrongContactNumberException() throws Exception {
		//given
		PerformanceDto registerDto = createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE,
			PERFORMANCE_END_DATE,
			PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
			PRICE, "01012345678", CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

		//when,then
		mockMvc.perform(post(PERFORMANCE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(registerDto)))
			.andExpect(jsonPath("$.errors[0].field").value("contactPhoneNum"))
			.andExpect(jsonPath("$.errors[0].reason").value(INVALID_CONTACT_NUMBER_ERROR_MESSAGE));
	}

	@Test
	@DisplayName("공연 등록 API : 공연 정보 길이 최댓값 초과시, 오류 메시지 반환 ")
	void performanceInformationMaximumValueException() throws Exception {
		//given
		PerformanceDto registerDto = createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE,
			PERFORMANCE_END_DATE,
			PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
			PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, createMaximumString(), PERFORMANCE_PLACE);

		//when,then
		mockMvc.perform(post(PERFORMANCE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(registerDto)))
			.andExpect(jsonPath("$.errors[0].field").value("performanceInfo"))
			.andExpect(jsonPath("$.errors[0].reason").value(PERFORMANCE_INFO_LENGTH_EXCEEDED_ERROR_MESSAGE));
	}

	@ParameterizedTest
	@MethodSource("registerValidityArgumentsList")
	@DisplayName("공연 등록 API : 필수 값 없을 시, Bed Request 반환")
	void performanceRegistrationMandatoryExceptions(PerformanceDto registerDto) throws Exception {
		mockMvc.perform(post(PERFORMANCE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(registerDto)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(INVALID_REGISTER_VALUE_ERROR_MESSAGE));
	}

	@Test
	@DisplayName("공연 등록 API : 잘못된 공연 시작 날짜 형식, 오류 메시지 반환")
	void incorrectPerformanceStartDateFormat() throws Exception {
		//given
		String wrongStartDate = "-------";
		PerformanceDto registerDto = createPerformanceRegisterDto(USER_ID, wrongStartDate,
			PERFORMANCE_END_DATE,
			PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
			PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

		//when, then
		mockMvc.perform(post(PERFORMANCE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(registerDto)))
			.andExpect(jsonPath("$.errors[0].field").value("performanceStartDate"))
			.andExpect(jsonPath("$.errors[0].reason").value(INCORRECT_PERFORMANCE_DATE_FORMAT_ERROR_MESSAGE));
	}

	@Test
	@DisplayName("공연 등록 API : 잘못된 공연 종료 날짜 형식, 오류 메시지 반환")
	void incorrectPerformanceEndDateFormat() throws Exception {
		//given
		String wrongEndDate = "-----------";
		PerformanceDto registerDto = createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE,
			wrongEndDate,
			PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
			PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

		//when, then
		mockMvc.perform(post(PERFORMANCE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(registerDto)))
			.andExpect(jsonPath("$.errors[0].field").value("performanceEndDate"))
			.andExpect(jsonPath("$.errors[0].reason").value(INCORRECT_PERFORMANCE_DATE_FORMAT_ERROR_MESSAGE));
	}

	@Test
	@DisplayName("공연 등록 API : 잘못된 공연 시간 형식, 오류 메시지 반환")
	void incorrectPerformanceTimeFormat() throws Exception {
		//given
		String wrongPerformanceTime = "--------";
		PerformanceDto registerDto = createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE,
			PERFORMANCE_END_DATE,
			Set.of(wrongPerformanceTime), PERFORMANCE_TYPE, AUDIENCE_COUNT,
			PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

		//when, then
		mockMvc.perform(post(PERFORMANCE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(registerDto)))
			.andExpect(jsonPath("$.errors[0].field").value("performanceTimes"))
			.andExpect(jsonPath("$.errors[0].reason").value(INCORRECT_PERFORMANCE_TIME_ERROR_MESSAGE));
	}

	private PerformanceDto createPerformanceRegisterDtoWithInvalidDate() {
		return PerformanceTestDataFactory.createPerformanceRegisterDto("2023-05-01", "2023-04-01");
	}

	private static PerformanceDto createPerformanceRegisterDto(String register, String performanceStartDt,
		String performanceEndDt,
		Set<String> performanceTimes, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum,
		String contactPersonName, String performanceIntroduction, String performancePlace) {
		return PerformanceTestDataFactory.createPerformanceRegisterDto(register, performanceStartDt, performanceEndDt,
			performanceTimes, performanceType,
			audienceCount, price, contactPhoneNum, contactPersonName, performanceIntroduction, performancePlace);
	}

	private static String createMaximumString() {
		return "#".repeat(PERFORMANCE_MAXIMUM_COUNT + 1);
	}

	static Stream<Arguments> registerValidityArgumentsList() {
		return Stream.of(
			Arguments.of(
				createPerformanceRegisterDto(null, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
					PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, null, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT, PRICE,
					CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, null, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT, PRICE,
					CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, null, AUDIENCE_COUNT,
					PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, null,
					PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
					null, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
					PRICE, null, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
					PRICE, CONTACT_PHONE_NUMBER, null, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
					PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, null, PERFORMANCE_PLACE)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
					PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, null)),
			Arguments.of(
				createPerformanceRegisterDto(USER_ID, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, null, PERFORMANCE_TYPE, AUDIENCE_COUNT,
					PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE))
		);
	}
}
