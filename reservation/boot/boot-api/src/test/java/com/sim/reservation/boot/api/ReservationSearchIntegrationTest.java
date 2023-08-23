package com.sim.reservation.boot.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sim.reservation.boot.dto.request.PerformanceSearchRequest;
import com.sim.reservation.boot.dto.response.ReservationInfoResponse;
import com.sim.reservation.boot.error.ErrorCode;
import com.sim.reservation.data.reservation.dto.PerformanceInfoDto;
import com.sim.reservation.data.reservation.error.ReservationInfoNotFoundException;
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
	private static final String VIEW_RESERVATION_AVAILABLE_URL = "/api/performances/available";
	private static final String RESERVED_PERFORMANCE_INFO_URL = "/api/reservations";

	@Test
	@DisplayName("예약 가능한 공연을 조회할 수 있다.")
	void returnPerformanceInformationWhenAllConditionsExist() throws Exception {
		// given
		PerformanceSearchRequest request = PerformanceSearchRequest.builder()
			.build();
		PerformanceInfoDto expectedPerformanceInfo = createPerformanceInfo();

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

	@DisplayName("예약한 공연 정보를 조회할 수 있다.")
	@Test
	void viewReservedPerformanceInformation() throws Exception {
	    // given
		Long reservationId = 1L;
		ReservationInfoResponse response = createReservationInfo(reservationId);
		given(reservationSearchService.getReservationInfo(reservationId)).willReturn(response);

	    // when, then
		mockMvc.perform(get(RESERVED_PERFORMANCE_INFO_URL + "/" + reservationId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(response.getId()))
			.andExpect(jsonPath("$.email").value(response.getEmail()))
			.andExpect(jsonPath("$.place").value(response.getPlace()));
	}

	@DisplayName("예약되지 않은 ID로 조회시 실패하고 예외 메시지를 반환한다.")
	@Test
	void invalidReservationIDFailed() throws Exception {
	    // given
		long reservationId = 1L;
		given(reservationSearchService.getReservationInfo(any())).willThrow(
			ReservationInfoNotFoundException.class);

	    // when, then
		mockMvc.perform(get(RESERVED_PERFORMANCE_INFO_URL + "/" + reservationId))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(
				ErrorCode.RESERVATION_INFORMATION_NOT_FOUND.getMessage()));
	}

	private static ReservationInfoResponse createReservationInfo(Long reservationId) {
		return ReservationInfoResponse.builder()
			.id(reservationId)
			.email("test@naver.com")
			.place("망월사")
			.build();
	}

	private static PerformanceInfoDto createPerformanceInfo() {
		return PerformanceInfoDto.builder()
			.name("홍길동")
			.info("홍길동 영화입니다.")
			.build();
	}

}