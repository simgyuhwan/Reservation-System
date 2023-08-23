package com.sim.reservation.boot.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sim.reservation.boot.dto.request.PerformanceSearchRequest;
import com.sim.reservation.boot.error.ErrorCode;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

/**
 * ReservationControllerTest.java
 * 예약 관련 공연 API 테스트
 *
 * @author sgh
 * @since 2023.04.12
 */
class ReservationSearchIntegrationTest extends ControllerTestSupport{
	private static String VIEW_RESERVATION_AVAILABLE_URL = "/api/performances/available";

	@Test
	@DisplayName("예약 가능한 공연을 조회할 수 있다.")
	void returnPerformanceInformationWhenAllConditionsExist() throws Exception {
		// given
		PerformanceSearchRequest request = PerformanceSearchRequest.builder()
			.build();
		PerformanceInfoDto expectedPerformanceInfo = createPerformanceInfo("홍길동", "홍길동 영화입니다.");

		PageImpl<PerformanceInfoDto> page = new PageImpl<>(List.of(expectedPerformanceInfo));
		given(reservationSearchService.getAvailablePerformances(any(), any()))
			.willReturn(page);

		// when, then
		mockMvc.perform(get(VIEW_RESERVATION_AVAILABLE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(request))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(1)))
			.andExpect(jsonPath("$.content[0].name").value(expectedPerformanceInfo.getName()))
			.andExpect(jsonPath("$.content[0].info").value(expectedPerformanceInfo.getInfo()));
	}

	@Test
	@DisplayName("잘못된 날짜 형식으로 공연을 조회하면 조회에 실패하고 예외 메시지를 반환한다.")
	void invalidDateFormatQuery() throws Exception{
		// given
		PerformanceSearchRequest request = PerformanceSearchRequest.builder()
			.build();
		given(reservationSearchService.getAvailablePerformances(any(), any())).willThrow(
			DateTimeParseException.class);

		// when, then
		mockMvc.perform(get(VIEW_RESERVATION_AVAILABLE_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message")
				.value(ErrorCode.INVALID_DATE_FORMAT.getMessage()));
	}


	private static PerformanceInfoDto createPerformanceInfo(String name, String info) {
		return PerformanceInfoDto.builder()
			.name(name)
			.info(info)
			.build();
	}

}