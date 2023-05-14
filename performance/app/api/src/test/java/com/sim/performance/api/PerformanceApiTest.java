package com.sim.performance.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sim.performance.dto.request.PerformanceCreateRequest;
import com.sim.performance.dto.request.PerformanceUpdateRequest;
import com.sim.performance.error.ErrorCode;
import com.sim.performance.error.PerformanceControllerAdvice;
import com.sim.performance.factory.PerformanceCreateRequestFactory;
import com.sim.performance.factory.PerformanceDtoFactory;
import com.sim.performance.factory.PerformanceFactory;
import com.sim.performance.factory.PerformanceStatusDtoFactory;
import com.sim.performance.factory.PerformanceUpdateRequestFactory;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import com.sim.performance.performancedomain.dto.PerformanceStatusDto;
import com.sim.performance.performancedomain.error.InvalidPerformanceDateException;
import com.sim.performance.performancedomain.error.NoContentException;
import com.sim.performance.performancedomain.error.NotPendingPerformanceException;
import com.sim.performance.performancedomain.error.PerformanceNotFoundException;
import com.sim.performance.performancedomain.service.PerformanceCommandService;
import com.sim.performance.performancedomain.service.PerformanceQueryService;

@ExtendWith(MockitoExtension.class)
public class PerformanceApiTest {
	private static final String PERFORMANCE_BASE_API_URL = "/api/performances";
	private static final Long MEMBER_ID = PerformanceFactory.MEMBER_ID;
	private static final String PERFORMANCE_NAME = PerformanceFactory.PERFORMANCE_NAME;
	private static final String PERFORMANCE_START_DATE = PerformanceFactory.PERFORMANCE_START_DATE;
	private static final String PERFORMANCE_END_DATE = PerformanceFactory.PERFORMANCE_END_DATE;
	private static final Set<String> PERFORMANCE_TIMES = PerformanceFactory.PERFORMANCE_TIMES;
	private static final String PERFORMANCE_TYPE = PerformanceFactory.PERFORMANCE_TYPE;
	private static final Integer AUDIENCE_COUNT = PerformanceFactory.AUDIENCE_COUNT;
	private static final Integer PRICE = PerformanceFactory.PRICE;
	private static final String CONTACT_PHONE_NUMBER = PerformanceFactory.CONTACT_PHONE_NUMBER;
	private static final String CONTACT_PERSON_NAME = PerformanceFactory.CONTACT_PERSON_NAME;
	private static final String PERFORMANCE_INFO = PerformanceFactory.PERFORMANCE_INFO;
	private static final String PERFORMANCE_PLACE = PerformanceFactory.PERFORMANCE_PLACE;
	private static final Long PERFORMANCE_ID = 1L;
	private static final String MEMBER_PARAM_KEY = "?memberId=" ;
	private MockMvc mockMvc;
	private Gson gson;
	public static final Integer PERFORMANCE_MAXIMUM_COUNT = 255;

	@InjectMocks
	private PerformanceController performanceController;

	@Mock
	private PerformanceCommandService performanceCommandService;

	@Mock
	private PerformanceQueryService performanceQueryService;

	@BeforeEach()
	void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(performanceController)
			.setControllerAdvice(PerformanceControllerAdvice.class)
			.build();
	}

	@Nested
	@DisplayName("공연 등록 API")
	class PerformanceRegistrationApiTest{
		public static final String INVALID_REGISTER_VALUE_ERROR_MESSAGE = "공연 등록 값이 올바르지 않습니다.";
		public static final String INVALID_CONTACT_NUMBER_ERROR_MESSAGE = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx";
		public static final String PERFORMANCE_INFO_LENGTH_EXCEEDED_ERROR_MESSAGE = "공연 정보는 최대 255자입니다.";
		public static final String INCORRECT_PERFORMANCE_DATE_FORMAT_ERROR_MESSAGE = "공연 날짜 형식이 잘못되었습니다. ex) '2024-01-01'";
		public static final String INCORRECT_PERFORMANCE_TIME_ERROR_MESSAGE = "공연 시간 형식이 잘못되었습니다. ex) '15:45'";
		public static final Integer PERFORMANCE_MAXIMUM_COUNT = 255;
		public static final String MIN_AUDIENCE_ERROR_MESSAGE = "관객 수는 반드시 10명 이상이어야 합니다.";

		@Test
		@DisplayName("공연 등록 성공 - 201 상태코드 반환")
		void performanceRegisterSuccessTest() throws Exception{
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest();
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(status().isCreated());
		}

		@Test
		@DisplayName("종료일이 시작일보다 먼저면 400 상태코드 반환")
		void incorrectRegistrationScheduleErrorReturned() throws Exception {
			//when
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest("2023-05-01",
				"2023-04-01");
			willThrow(InvalidPerformanceDateException.class)
				.given(performanceCommandService)
				.createPerformance(any());

			ResultActions result = mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(performanceCreateRequest)));

			// then
			result.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("수용 관객, 최소 값 이상이 아니면 오류 메시지 반환")
		void minimumAudienceException() throws Exception {
			//given
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE,
				PERFORMANCE_END_DATE,
				PERFORMANCE_TIMES, PERFORMANCE_TYPE, 1,
				PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

			//when,then
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(jsonPath("$.errors[0].field").value("audienceCount"))
				.andExpect(jsonPath("$.errors[0].reason").value(MIN_AUDIENCE_ERROR_MESSAGE));
		}

		@Test
		@DisplayName("잘못된 담당자 핸드폰 번호 입력시, 오류 메시지 반환")
		void wrongContactNumberException() throws Exception {
			//given
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE,
				PERFORMANCE_END_DATE,
				PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
				PRICE, "01012345678", CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

			//when,then
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(jsonPath("$.errors[0].field").value("contactPhoneNum"))
				.andExpect(jsonPath("$.errors[0].reason").value(INVALID_CONTACT_NUMBER_ERROR_MESSAGE));
		}

		@Test
		@DisplayName("공연 정보 길이 최댓값 초과시, 오류 메시지 반환 ")
		void performanceInformationMaximumValueException() throws Exception {
			//given
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE,
				PERFORMANCE_END_DATE,
				PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
				PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, createMaximumString(), PERFORMANCE_PLACE);

			//when,then
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(jsonPath("$.errors[0].field").value("performanceInfo"))
				.andExpect(jsonPath("$.errors[0].reason").value(PERFORMANCE_INFO_LENGTH_EXCEEDED_ERROR_MESSAGE));
		}

		@ParameterizedTest
		@MethodSource("invalidRegistrationValues")
		@DisplayName("필수 값 없을 시, Bed Request 반환")
		void performanceRegistrationMandatoryExceptions(PerformanceCreateRequest performanceCreateRequest) throws Exception {
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(INVALID_REGISTER_VALUE_ERROR_MESSAGE));
		}

		@Test
		@DisplayName("잘못된 공연 시작 날짜 형식, 오류 메시지 반환")
		void incorrectPerformanceStartDateFormat() throws Exception {
			//given
			String wrongStartDate = "-------";
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest(MEMBER_ID,
				PERFORMANCE_NAME, wrongStartDate,
				PERFORMANCE_END_DATE,
				PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
				PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

			//when, then
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(jsonPath("$.errors[0].field").value("performanceStartDate"))
				.andExpect(jsonPath("$.errors[0].reason").value(INCORRECT_PERFORMANCE_DATE_FORMAT_ERROR_MESSAGE));
		}

		@Test
		@DisplayName("잘못된 공연 종료 날짜 형식, 오류 메시지 반환")
		void incorrectPerformanceEndDateFormat() throws Exception {
			//given
			String wrongEndDate = "-----------";
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE,
				wrongEndDate,
				PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
				PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

			//when, then
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(jsonPath("$.errors[0].field").value("performanceEndDate"))
				.andExpect(jsonPath("$.errors[0].reason").value(INCORRECT_PERFORMANCE_DATE_FORMAT_ERROR_MESSAGE));
		}

		@Test
		@DisplayName("잘못된 공연 시간 형식, 오류 메시지 반환")
		void incorrectPerformanceTimeFormat() throws Exception {
			//given
			String wrongPerformanceTime = "--------";
			PerformanceCreateRequest performanceCreateRequest = createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE,
				PERFORMANCE_END_DATE,
				Set.of(wrongPerformanceTime), PERFORMANCE_TYPE, AUDIENCE_COUNT,
				PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE);

			//when, then
			mockMvc.perform(post(PERFORMANCE_BASE_API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceCreateRequest)))
				.andExpect(jsonPath("$.errors[0].field").value("performanceTimes"))
				.andExpect(jsonPath("$.errors[0].reason").value(INCORRECT_PERFORMANCE_TIME_ERROR_MESSAGE));
		}
		static Stream<Arguments> invalidRegistrationValues() {
			return Stream.of(
				Arguments.of(
					createPerformanceCreateRequest(null, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
						PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, null, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT, PRICE,
						CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, null, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT, PRICE,
						CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, null, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT, PRICE,
						CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, null, PERFORMANCE_TYPE, AUDIENCE_COUNT,
						PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, null, AUDIENCE_COUNT,
						PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, null,
						PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
						null, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
						PRICE, null, CONTACT_PERSON_NAME, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
						PRICE, CONTACT_PHONE_NUMBER, null, PERFORMANCE_INFO, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
						PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, null, PERFORMANCE_PLACE)),
				Arguments.of(
					createPerformanceCreateRequest(MEMBER_ID, PERFORMANCE_NAME, PERFORMANCE_START_DATE, PERFORMANCE_END_DATE, PERFORMANCE_TIMES, PERFORMANCE_TYPE, AUDIENCE_COUNT,
						PRICE, CONTACT_PHONE_NUMBER, CONTACT_PERSON_NAME, PERFORMANCE_INFO, null))
			);
		}
	}

	@Nested
	@DisplayName("공연 수정 API")
	class PerformanceInformationEditingApiTest {
		@Test
		@DisplayName("등록된 공연 정보가 없을 때, 예외 발생 및 오류 메시지 반환")
		void noPerformanceRegisteredWithUserIdException() throws Exception {
			//given
			long performanceId = 1L;
			PerformanceUpdateRequest performanceUpdateRequest = createPerformanceUpdateRequest();
			willThrow(PerformanceNotFoundException.class)
				.given(performanceCommandService)
				.updatePerformance(any(), any());

			//when,then
			mockMvc.perform(put(PERFORMANCE_BASE_API_URL + "/" + performanceId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceUpdateRequest)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(ErrorCode.NO_REGISTERED_PERFORMANCE_INFORMATION.getMessage()));
		}

		@Test
		@DisplayName("공연 수정 성공, 200 반환")
		void performanceModificationSuccessTest() throws Exception {
			//given
			long performanceId = 1L;
			PerformanceUpdateRequest performanceUpdateRequest = createPerformanceUpdateRequest();
			PerformanceDto performanceDto = createPerformanceDto();
			when(performanceCommandService.updatePerformance(any(), any())).thenReturn(performanceDto);

			// when, then
			mockMvc.perform(put(PERFORMANCE_BASE_API_URL + "/" + performanceId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceUpdateRequest)))
				.andExpect(status().isOk());
		}

		@Test
		@DisplayName("공연 수정 성공, 반환 값 일치 확인")
		void checkPerformanceModificationSuccessReturnValueMatching() throws Exception {
			//given
			long performanceId = 1L;
			PerformanceDto performanceDto = createPerformanceDto();
			when(performanceCommandService.updatePerformance(any(), any())).thenReturn(performanceDto);

			//when, then
			mockMvc.perform(put(PERFORMANCE_BASE_API_URL + "/" + performanceId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(performanceDto)))
				.andExpect(jsonPath("$.memberId").value(performanceDto.getMemberId()))
				.andExpect(jsonPath("$.contactPhoneNum").value(performanceDto.getContactPhoneNum()))
				.andExpect(jsonPath("$.contactPersonName").value(performanceDto.getContactPersonName()));
		}
	}

	@Nested
	@DisplayName("회원 ID로 등록된 공연 조회 API")
	class PerformanceSearchApiWithUserIDTest {

		@Test
		@DisplayName("회원 ID로 등록된 공연이 없을 시, 400 반환")
		void ReturnsBadRequestWhenThereIsNoRegisteredPerformances() throws Exception {
			//given
			willThrow(NoContentException.class)
				.given(performanceQueryService)
				.selectPerformances(MEMBER_ID);
			//when,then
			mockMvc.perform(get(PERFORMANCE_BASE_API_URL + MEMBER_PARAM_KEY+ MEMBER_ID))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("회원 ID로 등록된 공연이 없을 시, 오류 메시지 반환")
		void ReturnsErrorMessageWhenThereIsNoRegisteredPerformances() throws Exception {
			//given
			willThrow(NoContentException.class)
				.given(performanceQueryService)
				.selectPerformances(MEMBER_ID);

			//when, then
			mockMvc.perform(get(PERFORMANCE_BASE_API_URL + MEMBER_PARAM_KEY + MEMBER_ID))
				.andExpect(jsonPath("$.message").value(ErrorCode.NO_REGISTERED_PERFORMANCE_INFORMATION.getMessage()));
		}

		@Test
		@DisplayName("공연 조회 성공, 200 반환")
		void returnOf200CodeForPerformanceSearchSuccess() throws Exception {
			mockMvc.perform(get(PERFORMANCE_BASE_API_URL + MEMBER_PARAM_KEY + MEMBER_ID))
				.andExpect(status().isOk());
		}

		@Test
		@DisplayName("공연 조회 성공, 데이터 일치 확인")
		void matchPerformanceSearchSuccessReturnData() throws Exception {
			//given
			List<PerformanceDto> performanceDtoList = createPerformanceDtoList();
			when(performanceQueryService.selectPerformances(MEMBER_ID)).thenReturn(performanceDtoList);

			//when
			MvcResult result = mockMvc.perform(get(PERFORMANCE_BASE_API_URL + MEMBER_PARAM_KEY + MEMBER_ID))
				.andExpect(status().isOk())
				.andReturn();

			//then
			TypeToken<List<PerformanceDto>> token = new TypeToken<>() {};
			List<PerformanceDto> returnedPerformanceDtoList = gson.fromJson(result.getResponse().getContentAsString(), token.getType());
			assertThat(performanceDtoList.size()).isEqualTo(returnedPerformanceDtoList.size());
		}

		private List<PerformanceDto> createPerformanceDtoList() {
			return List.of(PerformanceDtoFactory.createPerformanceDto());
		}
	}

	@Nested
	@DisplayName("공연 ID를 통한 공연 상세정보 조회")
	class PerformanceSelectByPerformanceIdTest{
		private static final String PERFORMANCE_SEARCH_URL = "/api/performances/";

		@Test
		@DisplayName("공연 ID에 일치하는 공연 정보 없음, 400 상태 코드 반환")
		void noMatchingPerformanceIDReturnCode400() throws Exception{
			when(performanceQueryService.selectPerformanceById(PERFORMANCE_ID)).thenThrow(PerformanceNotFoundException.class);
			mockMvc.perform(get(PERFORMANCE_SEARCH_URL + PERFORMANCE_ID))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("공연 ID에 일치하는 공연 정보 없음, 에러 메시지 확인")
		void noMatchingPerformanceIDCheckErrorMessage() throws Exception {
			when(performanceQueryService.selectPerformanceById(PERFORMANCE_ID)).thenThrow(PerformanceNotFoundException.class);
			mockMvc.perform(get(PERFORMANCE_SEARCH_URL + PERFORMANCE_ID))
				.andExpect(jsonPath("$.message").value(ErrorCode.NO_REGISTERED_PERFORMANCE_INFORMATION.getMessage()));
		}

		@Test
		@DisplayName("공연 ID로 공연 상세 정보 반환 성공")
		void returnPerformanceDetailsByPerformanceID() throws Exception {
			when(performanceQueryService.selectPerformanceById(PERFORMANCE_ID)).thenReturn(createPerformanceDto());
			mockMvc.perform(get(PERFORMANCE_SEARCH_URL + PERFORMANCE_ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.memberId").value(MEMBER_ID))
				.andExpect(jsonPath("$.performanceName").value(PERFORMANCE_NAME));
		}
	}

	@Nested
	@DisplayName("공연 등록 신청중인 공연 정보 조회 API")
	class SearchPerformanceInfoForRegistrationTest {
		private static final String PERFORMANCE_PENDING_URL = "/api/performances/" + PERFORMANCE_ID + "/status/pending";

		@Test
		@DisplayName("공연 등록 중인 공연 정보가 없을 시, 400 상태 코드 반환")
		void noMatchingPerformanceIdReturn400Code() throws Exception {
			when(performanceQueryService.selectPendingPerformanceById(PERFORMANCE_ID)).thenThrow(
				NotPendingPerformanceException.class);
			mockMvc.perform(get(PERFORMANCE_PENDING_URL))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("공연 등록 중인 공연 정보가 없을 시, 오류 메시지 반환")
		void noMatchingPerformanceIdReturnErrorMessage() throws Exception {
			when(performanceQueryService.selectPendingPerformanceById(PERFORMANCE_ID)).thenThrow(
				NotPendingPerformanceException.class);
			mockMvc.perform(get(PERFORMANCE_PENDING_URL))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOUND_PENDING_PERFORMANCE.getMessage()));
		}

		@Test
		@DisplayName("공연 등록 중인 공연 정보 조회 성공, 200 상태 코드 반환")
		void searchPerformanceInfoCurrentlyBeingRegistered() throws Exception {
			when(performanceQueryService.selectPendingPerformanceById(PERFORMANCE_ID)).thenReturn(createPerformanceDto());
			mockMvc.perform(get(PERFORMANCE_PENDING_URL))
				.andExpect(status().isOk());
		}
	}

	@Nested
	@DisplayName("공연 등록 상태 조회 API")
	class PerformanceRegistrationStatusInquiryTest{
		private static final String PERFORMANCE_STATUS_URL = "/api/performances/" + PERFORMANCE_ID + "/status";

		@Test
		@DisplayName("공연 등록 중인 공연 정보가 없을 시, 400 상태 코드 반환")
		void noMatchingPerformanceReturn400Code() throws Exception {
			when(performanceQueryService.getPerformanceStatusByPerformanceId(PERFORMANCE_ID)).thenThrow(PerformanceNotFoundException.class);
			mockMvc.perform(get(PERFORMANCE_STATUS_URL))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("공연 등록 중인 공연 정보가 없을 시, 오류 메시지 반환")
		void noMatchingPerformanceReturnErrorMessage() throws Exception {
			when(performanceQueryService.getPerformanceStatusByPerformanceId(PERFORMANCE_ID)).thenThrow(PerformanceNotFoundException.class);
			mockMvc.perform(get(PERFORMANCE_STATUS_URL))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(ErrorCode.NO_REGISTERED_PERFORMANCE_INFORMATION.getMessage()));
		}

		@Test
		@DisplayName("공연 등록 중인 공연 정보 조회 성공, 200 상태 코드 반환")
		void matchingPerformanceReturn200Code() throws Exception {
			when(performanceQueryService.getPerformanceStatusByPerformanceId(PERFORMANCE_ID)).thenReturn(createPerformanceStatusDto());
			mockMvc.perform(get(PERFORMANCE_STATUS_URL))
				.andExpect(status().isOk());
		}

		@Test
		@DisplayName("공연 등록 중인 공연 정보 조회 성공, 상태 값 확인")
		void matchingPerformanceCheckReturnValue() throws Exception {
			// given
			PerformanceStatusDto performanceStatusDto = createPerformanceStatusDto();
			when(performanceQueryService.getPerformanceStatusByPerformanceId(PERFORMANCE_ID)).thenReturn(performanceStatusDto);

			// when
			ResultActions result = mockMvc.perform(get(PERFORMANCE_STATUS_URL)).andExpect(status().isOk());

			// then
			result.andExpect(jsonPath("$.message").value(performanceStatusDto.getMessage()))
				.andExpect(jsonPath("$.performanceId").value(performanceStatusDto.getPerformanceId()));
		}

		private PerformanceStatusDto createPerformanceStatusDto() {
			return PerformanceStatusDtoFactory.createPerformanceStatusDto();
		}
	}

	private PerformanceDto createPerformanceDto() {
		return PerformanceDtoFactory.createPerformanceDto();
	}

	private static PerformanceDto createPerformanceDto(Long memberId, String performanceName, String performanceStartDt,
		String performanceEndDt,
		Set<String> performanceTimes, String performanceType, Integer audienceCount, Integer price,
		String contactPhoneNum,
		String contactPersonName, String performanceIntroduction, String performancePlace) {
		return PerformanceDtoFactory.createPerformanceDto(memberId,performanceName, performanceStartDt, performanceEndDt,
			performanceTimes, performanceType,
			audienceCount, price, contactPhoneNum, contactPersonName, performanceIntroduction, performancePlace);
	}

	private static String createMaximumString() {
		return "#".repeat(PERFORMANCE_MAXIMUM_COUNT + 1);
	}


	private PerformanceCreateRequest createPerformanceCreateRequest(){
		return PerformanceCreateRequestFactory.createPerformanceCreateRequest();
	}
	private PerformanceCreateRequest createPerformanceCreateRequest(String startDate, String endDate){
		return PerformanceCreateRequestFactory.createPerformanceCreateRequest(startDate, endDate);
	}

	private static PerformanceCreateRequest createPerformanceCreateRequest(Long memberId, String performanceName,
		String startDate,
		String endDate, Set<String> performanceTimes, String performanceType, Integer audienceCount, Integer price,
		 String contactPhoneNum, String contactPerformanceName, String performanceInfo, String performancePlace){
		return PerformanceCreateRequestFactory.createPerformanceCreateRequest(memberId, performanceName, startDate,
			endDate, performanceTimes,performanceType, audienceCount, price,
			contactPerformanceName, contactPhoneNum, performanceInfo, performancePlace);
	}

	private PerformanceUpdateRequest createPerformanceUpdateRequest() {
		return PerformanceUpdateRequestFactory.createPerformanceUpdateRequest();
	}
}