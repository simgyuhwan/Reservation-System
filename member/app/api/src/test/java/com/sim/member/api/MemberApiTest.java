package com.sim.member.api;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
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
import com.sim.member.api.factory.MemberDtoFactory;
import com.sim.member.api.factory.MemberPerformanceDtoFactory;
import com.sim.member.api.factory.MemberUpdateRequestFactory;
import com.sim.member.api.factory.PerformanceDtoFactory;
import com.sim.member.dto.request.MemberUpdateRequest;
import com.sim.member.error.ErrorCode;
import com.sim.member.error.MemberControllerAdvice;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;
import com.sim.member.memberdomain.dto.PerformanceDto;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.error.MemberNotFoundException;
import com.sim.member.memberdomain.service.MemberCommandService;
import com.sim.member.memberdomain.service.MemberQueryService;

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
	private final static String USER_ID = MemberDtoFactory.USER_ID;
	private final static String PHONE_NUM = MemberDtoFactory.PHONE_NUM;
	private final static String USERNAME = MemberDtoFactory.USERNAME;
	private final static String ADDRESS = MemberDtoFactory.ADDRESS;
	private final static Long MEMBER_ID = MemberDtoFactory.MEMBER_ID;

	private MockMvc mockMvc;
	private final Gson gson = new Gson();

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
	@DisplayName("memberId로 회원 정보 조회 API 테스트")
	class MemberLookupApiTestByMemberIdTest {
		private final String MEMBER_SEARCH_URL = MEMBER_API_URL + "/" + MEMBER_ID;

		@Test
		@DisplayName("memberId로 등록된 회원이 없을 시, 400 상태 코드 반환")
		void return400StatusCodeWhenNoRegisteredMember() throws Exception {
			when(memberQueryService.findMemberById(MEMBER_ID)).thenThrow(MemberNotFoundException.class);
			mockMvc.perform(get(MEMBER_SEARCH_URL)).andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("memberId로 등록된 회원이 없을 시, 오류 메시지 확인")
		void checkUnregisteredMemberErrorMessage() throws Exception {
			when(memberQueryService.findMemberById(MEMBER_ID)).thenThrow(MemberNotFoundException.class);
			mockMvc.perform(get(MEMBER_SEARCH_URL))
				.andExpect(jsonPath("$.message").value(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
		}

		@Test
		@DisplayName("등록된 회원 정보 조회 성공, 200 상태 코드 반환")
		void memberInquirySuccess200StatusCodeReturned() throws Exception {
			when(memberQueryService.findMemberById(MEMBER_ID)).thenReturn(createMemberDto());
			mockMvc.perform(get(MEMBER_SEARCH_URL)).andExpect(status().isOk());
		}

		@Test
		@DisplayName("등록된 회원 정보 조회 성공, 응답 값 확인")
		void checkMemberInquirySuccessResponseValue() throws Exception {
			// given
			MemberDto memberDto = createMemberDto();
			when(memberQueryService.findMemberById(MEMBER_ID)).thenReturn(memberDto);

			// when
			ResultActions result = mockMvc.perform(get(MEMBER_SEARCH_URL));

			// then
			result.andExpect(jsonPath("$.userId").value(memberDto.getUserId()))
				.andExpect(jsonPath("$.phoneNum").value(memberDto.getPhoneNum()))
				.andExpect(jsonPath("$.username").value(memberDto.getUsername()));
		}


	}

	@Nested
	@DisplayName("로그인 ID로 회원 정보 조회 API 테스트")
	class MemberLookupAPITestByUserIDTest {
		private final String USER_PARAM_KEY = "?userId=";

		@Test
		@DisplayName("성공 테스트, 응답 값 확인 테스트")
		void memberInquirySuccessTest() throws Exception {
			//given
			MemberDto memberDto = createMemberDto();

			//when
			when(memberQueryService.findMemberByUserId(USER_ID)).thenReturn(memberDto);

			//then
			mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
				.andExpect(jsonPath("$.userId", is(USER_ID)))
				.andExpect(jsonPath("$.phoneNum", is(PHONE_NUM)))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.address", is(ADDRESS)));
		}

		@Test
		@DisplayName("성공 테스트, 200 상태 코드 반환")
		void memberInquiry200CodeVerificationTest() throws Exception {
			//given
			MemberDto memberDto = createMemberDto();

			//when
			when(memberQueryService.findMemberByUserId(USER_ID)).thenReturn(memberDto);

			//then
			mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
				.andExpect(status().isOk());
		}

		@Test
		@DisplayName("일치하는 로그인 ID 없음, 400 상태 코드 반환")
		void noMatchingUserIdTestFailed() throws Exception {
			when(memberQueryService.findMemberByUserId(USER_ID)).thenThrow(InvalidUserIdException.class);
			mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("일치하는 로그인 ID 없음, 오류 메시지 반환")
		void noMatchingLoginIDReturnErrorMessage() throws Exception {
			when(memberQueryService.findMemberByUserId(USER_ID)).thenThrow(InvalidUserIdException.class);
			mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
				.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_USER_ID_VALUE.getMessage()));
		}
	}

	@Nested
	@DisplayName("회원 정보 수정 API 테스트")
	class MemberInformationModificationAPITest {
		@Test
		@DisplayName("존재하지 않는 로그인 ID 요청시, 400 상태 코드 반환")
		void putApiNoMatchingUserIdTestFailed() throws Exception {
			//when
			when(memberCommandService.updateMemberInfo(any(), any())).thenThrow(
				InvalidUserIdException.class);
			MemberUpdateRequest memberUpdateRequest = createUpdateMemberRequest();

			//then
			String userIDDoesNotExist = "userIDDoesNotExist";
			mockMvc.perform(put(MEMBER_API_URL + "/" + userIDDoesNotExist)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(memberUpdateRequest)))
				.andExpect(status().isBadRequest());
		}

		@ParameterizedTest
		@MethodSource("updateValidityArgumentsList")
		@DisplayName("수정 요청 값이 잘못된 값일 때, 400 상태 코드 반환")
		void invalidCorrectionRequestValueExceptionTest(String userId, String phoneNum, String username,
			String address) throws Exception {
			//when
			MemberUpdateRequest memberUpdateRequest = createUpdateMemberRequest(userId, phoneNum, username, address);

			//then
			mockMvc.perform(put(MEMBER_API_URL + "/" + USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(memberUpdateRequest)))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("수정 성공, 200 상태 코드 반환")
		void memberInformationModificationSuccessTest() throws Exception {
			//given
			MemberDto memberDto = createMemberDto();
			MemberUpdateRequest memberUpdateRequest = createUpdateMemberRequest();

			//when
			when(memberCommandService.updateMemberInfo(any(), any())).thenReturn(memberDto);

			//then
			mockMvc.perform(put(MEMBER_API_URL + "/" + USER_ID)
					.contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(memberUpdateRequest)))
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

		private MemberUpdateRequest createUpdateMemberRequest() {
			return MemberUpdateRequestFactory.createMemberUpdateRequest();
		}

		private MemberUpdateRequest createUpdateMemberRequest(String userId, String username, String phoneNum, String address) {
			return MemberUpdateRequestFactory.createMemberUpdateRequest(userId, username, phoneNum, address);
		}
	}

	@Nested
	@DisplayName("회원 ID로 등록된 공연 정보 조회")
	class SearchPerformanceRegisteredByUserIdTest {
		private static final String VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL = "/api/members/" + MEMBER_ID + "/performances";

		@Test
		@DisplayName("등록된 공연 조회 성공, 필드 값 확인")
		void registeredPerformanceSearchSuccess() throws Exception{
			//given
			MemberPerformanceDto memberPerformanceDto = createMemberPerformanceDto(USER_ID,
				USERNAME);

			when(memberQueryService.selectPerformancesById(MEMBER_ID)).thenReturn(memberPerformanceDto);

			//when
			ResultActions result = mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
				.andExpect(status().isOk());

			//then
			result.andExpect(jsonPath("$.userId").value(USER_ID))
				.andExpect(jsonPath("$.userName").value(USERNAME));
		}

		@Test
		@DisplayName("유효하지 않은 회원 ID, 400 상태 코드 반환")
		void returnUnregisteredMember400Code() throws Exception{
			when(memberQueryService.selectPerformancesById(MEMBER_ID)).thenThrow(MemberNotFoundException.class);
			mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
				.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("유효하지 않은 회원 ID, 에러 메시지 확인")
		void returnErrorMessageWhenThereIsNoRegisteredMember() throws Exception {
			when(memberQueryService.selectPerformancesById(MEMBER_ID)).thenThrow(MemberNotFoundException.class);
			mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
				.andExpect(jsonPath("$.message").value(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
		}

		private MemberPerformanceDto createMemberPerformanceDto(String userId, String username) {
			return MemberPerformanceDtoFactory.createMemberPerformanceDto(userId, username);
		}

		private static List<PerformanceDto> createPerformanceDtoList() {
			return PerformanceDtoFactory.createPerformanceDtoList();
		}
	}

	private static MemberDto createMemberDto() {
		return MemberDtoFactory.createMemberDto();
	}

}
