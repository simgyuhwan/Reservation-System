package com.reservation.api;

import static com.reservation.factory.MemberTestConstants.*;
import static com.reservation.factory.MemberTestDataFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.reservation.factory.MemberTestDataFactory;
import com.reservation.memberservice.api.MemberController;
import com.reservation.memberservice.application.MemberCommandService;
import com.reservation.memberservice.application.MemberQueryService;
import com.reservation.memberservice.dto.request.UpdateMemberDto;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import com.reservation.memberservice.error.MemberControllerAdvice;
import com.reservation.memberservice.error.MemberNotFoundException;

/**
 * MemberApiTest.java
 * 회원 API Test
 *
 * @author sgh
 * @since 2023.03.23
 */
@ExtendWith(MockitoExtension.class)
public class MemberApiTest {
	private final static String MEMBER_API_URL = "/api/members";

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

	@Test
	@DisplayName("맴버 조회 API: 성공 테스트, 응답 값 확인 테스트")
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
	@DisplayName("맴버 조회 API: 성공 테스트, 응답 코드 200 확인 테스트")
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
	@DisplayName("맴버 조회 API : 일치하는 userId 없음 실패 테스트")
	void noMatchingUserIdTestFailed() throws Exception {
		//when
		when(memberQueryService.findMemberByUserId(USER_ID)).thenThrow(MemberNotFoundException.class);

		//then
		mockMvc.perform(get(MEMBER_API_URL + "/" + USER_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("맴버 수정 API : 존재하지 않는 userId 요청시, 응답 코드 404 테스트")
	void putApiNoMatchingUserIdTestFailed() throws Exception {
		//when
		when(memberCommandService.updateMemberInfo(any(), any())).thenThrow(
			MemberNotFoundException.class);

		//then
		mockMvc.perform(put(MEMBER_API_URL + "/" + userIDDoesNotExist)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(MemberTestDataFactory.createUpdateMemberDto())))
			.andExpect(status().isNotFound());
	}

	@ParameterizedTest
	@MethodSource("updateValidityArgumentsList")
	@DisplayName("맴버 수정 API : 수정 요청 값이 잘못된 값일 때 예외 테스트")
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
	@DisplayName("맴버 수정 API : 수정 성공, 응답 코드 200 확인 테스트")
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
