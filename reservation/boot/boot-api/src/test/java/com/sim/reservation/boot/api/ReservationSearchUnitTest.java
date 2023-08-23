package com.sim.reservation.boot.api;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.sim.reservation.boot.error.ReservationSearchControllerAdvice;
import com.sim.reservation.boot.service.ReservationSearchService;
import com.sim.reservation.boot.util.QueryParameter;
import com.sim.reservation.boot.util.QueryParameterUtils;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * ReservationControllerTest.java
 * 예약 관련 공연 API 테스트
 *
 * @author sgh
 * @since 2023.04.12
 */
@ExtendWith(MockitoExtension.class)
class ReservationSearchUnitTest {

	private static final String START_DATE_KEY = "startDate";
	private static final String END_DATE_KEY = "endDate";
	private static final String NAME_KEY = "name";
	private static final String START_TIME_KEY = "startTime";
	private static final String END_TIME_KEY = "endTime";
	private static final String TYPE_KEY = "type";
	private static final String PLACE_KEY = "place";
	private static final String PAGE_KEY = "page";
	private static final String SIZE_KEY = "size";
	private static final String START_DATE_VALUE = "2045-01-01";
	private static final String END_DATE_VALUE = "2055-01-01";
	private static final String NAME_VALUE = "바람과 함께 사라지다";
	private static final String START_TIME_VALUE = "11:00";
	private static final String END_TIME_VALUE = "14:00";
	private static final String TYPE_VALUE = "MUSICAL";
	private static final String PLACE_VALUE = "홍대 1극장";
	private static final Integer PAGE_VALUE = 0;
	private static final Integer SIZE_VALUE = 10;

	private static final String VIEW_RESERVATION_STATUS_URL = "/api/performances/available?";
	private MockMvc mockMvc;

	@InjectMocks
	private ReservationSearchController reservationSearchController;

	@Mock
	private ReservationSearchService reservationSearchService;

	@BeforeEach
	void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(reservationSearchController)
			.setControllerAdvice(ReservationSearchControllerAdvice.class)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("원하는 조건으로 공연 정보를 조회할 수 있다.")
	void returnPerformanceInformationWhenAllConditionsExist() throws Exception {
		//given
		String name = "공연 이름";
		String info = "공연 소개";
		String type = "MUSICAL";

		given(reservationSearchService.getAvailablePerformances(any(), any())).willReturn(
			createPerformanceInfoPage(name, info, type));

		//when
		ResultActions result = mockMvc.perform(get(VIEW_RESERVATION_STATUS_URL + createNormalQueryStrings()))
			.andExpect(status().isOk());

		//then
		result.andExpect(jsonPath("$.content[0].name").value(name))
			.andExpect(jsonPath("$.content[0].info").value(info))
			.andExpect(jsonPath("$.content[0].type").value(type));
	}

	@Test
	@DisplayName("원하는 조건으로 공연 정보 조회시 200 상태 코드를 반환한다.")
	void allConditionsPresentReturnCode200() throws Exception {
		// given
		String NORMAL_PARAMETERS = createNormalQueryStrings();

		// when, then
		mockMvc.perform(get(VIEW_RESERVATION_STATUS_URL + NORMAL_PARAMETERS)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
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

		return QueryParameterUtils.buildQueryString(queryParameters);
	}

	private Page<PerformanceInfoDto> createPerformanceInfoPage(String name, String info,
		String type) {
		PerformanceInfoDto performanceInfoDto = PerformanceInfoDto.builder()
			.name(name)
			.info(info)
			.type(type)
			.place("홍대 1극장")
			.isAvailable(true)
			.price(15000)
			.contactPhoneNum("010-1234-4569")
			.contactPersonName("홍길동")
			.build();

		List<PerformanceInfoDto> performanceInfoDtoList = List.of(performanceInfoDto);
		PageRequest pageable = PageRequest.of(1, 15);
		return new PageImpl<>(performanceInfoDtoList, pageable, performanceInfoDtoList.size());
	}
}