package com.reservation.member.api;

import static com.reservation.member.global.factory.MemberTestDataFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.reservation.member.application.MemberCommandService;
import com.reservation.member.application.MemberQueryService;
import com.reservation.member.dto.response.MemberInfoDto;
import com.reservation.member.error.MemberControllerAdvice;
import com.reservation.member.error.MemberNotFoundException;

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
	@DisplayName("맴버 조회 API: 성공 테스트")
	void memberInquirySuccessTest() throws Exception {
		//given
		MemberInfoDto memberInfoDto = createMemberInfoDto();

		//when
		when(memberQueryService.findMemberByUserId(USER_ID)).thenReturn(memberInfoDto);

		//then
		mockMvc.perform(get(MEMBER_API_URL + "/" + USER_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userId", is(USER_ID)))
			.andExpect(jsonPath("$.phoneNum", is(PHONE_NUM)))
			.andExpect(jsonPath("$.username", is(USERNAME)))
			.andExpect(jsonPath("$.address", is(ADDRESS)));
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
}
