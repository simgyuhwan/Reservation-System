package com.reservation.api;

import static com.reservation.factory.MemberFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.reservation.common.error.ErrorCode;
import com.reservation.factory.MemberFactory;
import com.reservation.factory.MemberPerformanceFactory;
import com.reservation.factory.PerformanceDtoFactory;
import com.reservation.memberservice.api.MemberController;
import com.reservation.memberservice.application.MemberCommandService;
import com.reservation.memberservice.application.MemberQueryService;
import com.reservation.memberservice.dto.request.UpdateMemberDto;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.dto.response.MemberPerformanceDto;
import com.reservation.memberservice.error.InvalidUserIdException;
import com.reservation.memberservice.error.MemberControllerAdvice;

/**
 * MemberApiTest.java
 * 회원 정보 API Test
 *
 * @author sgh
 * @since 2023.03.23
 */
@ExtendWith(MockitoExtension.class)
public class MemberApiTest {
	private final static String MEMBER_API_URL = "/api/members";
	public final static String USER_ID = MemberFactory.USER_ID;
	public final static String PHONE_NUM = MemberFactory.PHONE_NUM;
	public final static String USERNAME = MemberFactory.USERNAME;
	public final static String ADDRESS = MemberFactory.ADDRESS;
	public final static String PASSWORD = MemberFactory.PASSWORD;

	private MockMvc mockMvc;
	private Gson gson = new Gson();
	private static String userIDDoesNotExist = "userIDDoesNotExist";

	@InjectMocks
	private MemberController memberController;

	@Mock
	private MemberCommandService memberCommandService;

	@Mock
	private MemberQueryService memberQueryService;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(memberController)
			.setControllerAdvice(MemberControllerAdvice.class)
			.build();
	}

	@Nested
	@DisplayName("회원 ID로 회원 정보 조회 API 테스트")
	class MemberLookupAPITestByUserIDTest {
		@Test
		@DisplayName("성공 테스트, 응답 값 확인 테스트")
		void memberInquirySuccessTest() throws Exception {
			//given
			MemberInfoDto memberInfoDto = createMemberInfoDto();

			//when
			when(memberQueryService.findMemberByUserId(USER_ID)).thenReturn(memberInfoDto);

			//then
			mockMvc.perform(get(MEMBER_API_URL + "/" + USER_ID)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.userId", is(USER_ID)))
				.andExpect(jsonPath("$.phoneNum", is(PHONE_NUM)))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.address", is(ADDRESS)));
		}

		@Test
		@DisplayName("성공 테스트, 200 상태 코드 반환")
		void memberInquiry200CodeVerificationTest() throws Exception {
			//given
			MemberInfoDto memberInfoDto = createMemberInfoDto();

			//when
			when(memberQueryService.findMemberByUserId(USER_ID)).thenReturn(memberInfoDto);

			//then
			mockMvc.perform(get(MEMBER_API_URL + "/" + USER_ID)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}

		@Test
		@DisplayName("일치하는 userId 없음, 400 상태 코드 반환")
		void noMatchingUserIdTestFailed() throws Exception {
			//when
			when(memberQueryService.findMemberByUserId(USER_ID)).thenThrow(InvalidUserIdException.class);

			//then
			mockMvc.perform(get(MEMBER_API_URL + "/" + USER_ID)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		}
	}

	@Nested
	@DisplayName("회원 정보 수정 API 테스트")
	class MemberInformationModificationAPITest {
		@Test
		@DisplayName("존재하지 않는 userId 요청시, 400 상태 코드 반환")
		void putApiNoMatchingUserIdTestFailed() throws Exception {
			//when
			when(memberCommandService.updateMemberInfo(any(), any())).thenThrow(
				InvalidUserIdException.class);

			//then
			mockMvc.perform(put(MEMBER_API_URL + "/" + userIDDoesNotExist)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(MemberFactory.createUpdateMemberDto())))
				.andExpect(status().isBadRequest());
		}

		@ParameterizedTest
		@MethodSource("updateValidityArgumentsList")
		@DisplayName("수정 요청 값이 잘못된 값일 때, 400 상태 코드 반환")
		void invalidCorrectionRequestValueExceptionTest(String userId, String phoneNum, String username,
			String address) throws Exception {
			//when
			UpdateMemberDto updateMemberDto = createUpdateMemberDto(userId, phoneNum, username, address);

			//then
			mockMvc.perform(put(MEMBER_API_URL + "/" + USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(updateMemberDto)))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("수정 성공, 200 상태 코드 반환")
		void memberInformationModificationSuccessTest() throws Exception {
			//given
			UpdateMemberDto updateMemberDto = createUpdateMemberDto();
			//when
			when(memberCommandService.updateMemberInfo(any(), any())).thenReturn(createMemberInfoDto());

			//then
			mockMvc.perform(put(MEMBER_API_URL + "/" + USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(updateMemberDto)))
				.andExpect(status().isOk());
		}

		static Stream<Arguments> updateValidityArgumentsList() {
			return Stream.of(
				Arguments.of("", "010-1234-5678", "username", "address"),
				Arguments.of("userId", "", "username", "Seoul"),
				Arguments.of("userId", "010-1234-5678", "", "Seoul"),
				Arguments.of("userId", "010-1234-5678", "username", ""),
				Arguments.of("userId", "01012345678", "username", ""),
				Arguments.of(null, "01012345678", "username", "address"),
				Arguments.of("userId", null, "username", "address"),
				Arguments.of("userId", "01012345678", null, "address"),
				Arguments.of("userId", "01012345678", "username", null)
			);
		}
	}

	@Nested
	@DisplayName("회원 ID로 등록된 공연 정보 조회")
	class SearchPerformanceRegisteredByUserIdTest {
		private static final String USER_ID = MemberPerformanceFactory.USER_ID;
		private static final String VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL = "/api/members/" + USER_ID + "/performances";

		@Test
		@DisplayName("등록된 공연 조회 성공, 필드 값 확인")
		void registeredPerformanceSearchSuccess() throws Exception{
			//given
			String PERFORMANCE_NAME = PerformanceDtoFactory.PERFORMANCE_NAME;
			String USERNAME = MemberPerformanceFactory.USER_NAME;
			when(memberQueryService.selectPerformancesByUserId(USER_ID)).thenReturn(createMemberPerformanceDto());

			//when
			ResultActions result = mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
				.andExpect(status().isOk());

			//then
			result.andExpect(jsonPath("$.userId").value(USER_ID))
				.andExpect(jsonPath("$.userName").value(USERNAME))
				.andExpect(jsonPath("$.performances[0].performanceName").value(PERFORMANCE_NAME));
		}

		@Test
		@DisplayName("유효하지 않은 회원 ID, 400 상태 코드 반환")
		void returnUnregisteredMember400Code() throws Exception{
			when(memberQueryService.selectPerformancesByUserId(USER_ID)).thenThrow(InvalidUserIdException.class);
			mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("유효하지 않은 회원 ID, 에러 메시지 확인")
		void returnErrorMessageWhenThereIsNoRegisteredMember() throws Exception {
			when(memberQueryService.selectPerformancesByUserId(USER_ID)).thenThrow(InvalidUserIdException.class);
			mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
				.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_USER_ID_VALUE.getMessage()));
		}

		private MemberPerformanceDto createMemberPerformanceDto() {
			return MemberPerformanceFactory.createMemberPerformanceDto();
		}
	}

}
