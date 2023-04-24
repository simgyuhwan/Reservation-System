package com.sim.reservationservice.api;

import static com.sim.reservationservice.factory.ReservationTestConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.reservation.common.error.ErrorCode;
import com.reservation.common.util.QueryParameter;
import com.reservation.common.util.QueryParameterUtils;
import com.sim.reservationservice.application.PerformanceQueryService;
import com.sim.reservationservice.dto.response.PerformanceInfoDto;
import com.sim.reservationservice.error.ReservationControllerAdvice;
import com.sim.reservationservice.factory.ReservationTestDataFactory;

/**
 * ReservationControllerTest.java
 * 예약 API 테스트
 *
 * @author sgh
 * @since 2023.04.12
 */
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
	private static String RESERVATION_BASE_URL = "/api/performances";
	private static String VIEW_RESERVATION_STATUS_URL = "/api/performances/available?";
	private MockMvc mockMvc;
	private Gson gson;

	@InjectMocks
	private ReservationController reservationController;

	@Mock
	private PerformanceQueryService performanceQueryService;

	@BeforeEach
	void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(reservationController)
			.setControllerAdvice(ReservationControllerAdvice.class)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
		gson = new Gson();
	}

	@Test
	@DisplayName("공연 예약 현황 API : 모든 조건이 존재할 때, 공연 정보 반환")
	void returnPerformanceInformationWhenAllConditionsExist() throws Exception {
		//given
		when(performanceQueryService.selectPerformances(any(), any())).thenReturn(createPerformanceInfoListForPage());
		//when
		ResultActions result = mockMvc.perform(get(VIEW_RESERVATION_STATUS_URL + createNormalQueryStrings()))
			.andExpect(status().isOk());
		//then
		result.andExpect(jsonPath("$[0].name").value(NAME))
			.andExpect(jsonPath("$[0].info").value(INFO))
			.andExpect(jsonPath("$[0].type").value(TYPE));
	}

	// @Test
	// @DisplayName("공연 예약 현황 API : 일치하는 조건이 없을 때, 빈 리스트 반환")
	// void returnsAnEmptyListIfNoneOfTheConditionsMatch() throws Exception {
	// 	//given
	// 	when(performanceQueryService.selectPerformances(any(), any())).thenReturn(Collections.emptyList());
	// 	//when
	// 	ResultActions result = mockMvc.perform(get(VIEW_RESERVATION_STATUS_URL + createNormalQueryStrings()))
	// 		.andExpect(status().isOk());
	// 	//then
	// 	result.andExpect(jsonPath("$").isArray())
	// 		.andExpect(jsonPath("$").isEmpty());
	// }

	@Test
	@DisplayName("공연 예약 현황 API : 모든 조건이 존재할 때, 200 상태 코드 반환")
	void allConditionsPresentReturnCode200() throws Exception {
		String NORMAL_PARAMETERS = createNormalQueryStrings();
		mockMvc.perform(get(VIEW_RESERVATION_STATUS_URL + NORMAL_PARAMETERS)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("공연 예약 현황 API : 현재일 보다 이전 날짜로 조회시, 400 코드 반환")
	void nonExistentConditionReturns400Code() throws Exception{
		mockMvc.perform(get( VIEW_RESERVATION_STATUS_URL + createDateQueryStrings("1999-01-01", "1999-01-01")))
		.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("공연 예약 현황 API : 현재일 보다 이전 날짜로 조회시, 오류 메시지 반환")
	void nonExistentConditionReturnsErrorMessage() throws Exception{
		mockMvc.perform(get( VIEW_RESERVATION_STATUS_URL + createDateQueryStrings("1999-01-01", "1999-01-01")))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(ErrorCode.RESERVATION_SEARCH_VALUE_INVALID.getMessage()));
	}

	@ParameterizedTest
	@MethodSource("wrongDateAndTimeQueryStrings")
	@DisplayName("공연 예약 현황 API : 잘못된 날짜, 시간 포맷시, 400 코드 반환")
	void invalidDateOrTimeFormatReturns400Code(String invalidDateString) throws Exception {
		mockMvc.perform(get(VIEW_RESERVATION_STATUS_URL + invalidDateString))
			.andExpect(status().isBadRequest());
	}

	private static String createNormalQueryStrings() {
		List<QueryParameter> queryParameters = new ArrayList<>();
		queryParameters.add(QueryParameter.of(START_DATE_KEY, START_DATE_VALUE));
		queryParameters.add(QueryParameter.of(END_DATE_KEY, END_DATE_VALUE));
		queryParameters.add(QueryParameter.of(NAME_KEY, NAME_VALUE));
		queryParameters.add(QueryParameter.of(START_TIME_KEY, START_TIME_VALUE));
		queryParameters.add(QueryParameter.of(END_TIME_KEY, END_TIME_VALUE));
		queryParameters.add(QueryParameter.of(TYPE_KEY, TYPE_VALUE));
		queryParameters.add(QueryParameter.of(PLACE_KEY, PLACE_VALUE));
		queryParameters.add(QueryParameter.of(PAGE_KEY, PAGE_VALUE));
		queryParameters.add(QueryParameter.of(SIZE_KEY, SIZE_VALUE));

		String parameters = QueryParameterUtils.buildQueryString(queryParameters);
		return parameters;
	}

	private static String createDateQueryStrings(String startDate, String endDate) {
		List<QueryParameter> queryParameters = new ArrayList<>();
		queryParameters.add(QueryParameter.of(START_DATE_KEY, startDate));
		queryParameters.add(QueryParameter.of(END_DATE_KEY, endDate));
		queryParameters.add(QueryParameter.of(NAME_KEY, NAME_VALUE));
		queryParameters.add(QueryParameter.of(START_TIME_KEY, START_TIME_VALUE));
		queryParameters.add(QueryParameter.of(END_TIME_KEY, END_TIME_VALUE));
		queryParameters.add(QueryParameter.of(TYPE_KEY, TYPE_VALUE));
		queryParameters.add(QueryParameter.of(PLACE_KEY, PLACE_VALUE));
		queryParameters.add(QueryParameter.of(PAGE_KEY, PAGE_VALUE));
		queryParameters.add(QueryParameter.of(SIZE_KEY, SIZE_VALUE));

		String parameters = QueryParameterUtils.buildQueryString(queryParameters);
		return parameters;
	}

	private static String createDateAndTimeQueryStrings(String startDate, String endDate, String startTime, String endTime) {
		List<QueryParameter> queryParameters = new ArrayList<>();
		queryParameters.add(QueryParameter.of(START_DATE_KEY, startDate));
		queryParameters.add(QueryParameter.of(END_DATE_KEY, endDate));
		queryParameters.add(QueryParameter.of(START_TIME_KEY, startTime));
		queryParameters.add(QueryParameter.of(END_TIME_KEY, endTime));
		queryParameters.add(QueryParameter.of(NAME_KEY, NAME_VALUE));
		queryParameters.add(QueryParameter.of(TYPE_KEY, TYPE_VALUE));
		queryParameters.add(QueryParameter.of(PLACE_KEY, PLACE_VALUE));
		queryParameters.add(QueryParameter.of(PAGE_KEY, PAGE_VALUE));
		queryParameters.add(QueryParameter.of(SIZE_KEY, SIZE_VALUE));

		String parameters = QueryParameterUtils.buildQueryString(queryParameters);
		return parameters;
	}

	static Stream<Arguments> wrongDateAndTimeQueryStrings() {
		return Stream.of(
			Arguments.of(
				createDateAndTimeQueryStrings("-----", END_DATE_VALUE, START_TIME_VALUE, END_TIME_VALUE)
			),
			Arguments.of(
				createDateAndTimeQueryStrings(START_DATE_VALUE, "-----", START_TIME_VALUE, END_TIME_VALUE)
			),
			Arguments.of(
				createDateAndTimeQueryStrings(START_DATE_VALUE, END_DATE_VALUE, "-----", END_TIME_VALUE)
			),
			Arguments.of(
				createDateAndTimeQueryStrings(START_DATE_VALUE, END_DATE_VALUE, START_TIME_VALUE, "-----")
			)
		);
	}

	private Page<PerformanceInfoDto> createPerformanceInfoListForPage() {
		return ReservationTestDataFactory.createPerformanceInfoListForPage();
	}
}