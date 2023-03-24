package com.reservation.member.api;

import static com.reservation.member.global.factory.MemberTestDataFactory.*;
import static org.mockito.BDDMockito.*;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.reservation.member.application.MemberCommandService;
import com.reservation.member.dto.request.SignUpDto;
import com.reservation.member.error.MemberControllerAdvice;
import com.reservation.member.global.factory.MemberTestDataFactory;

/**
 * MemberController.java
 * 회원 컨트롤러 테스트
 *
 * @author sgh
 * @since 2023.03.17
 */
@ExtendWith(MockitoExtension.class)
public class SignUpApiTest {
	private final static String SIGNUP_URL = "/api/signup";

	private MockMvc mockMvc;
	private Gson gson = new Gson();

	@InjectMocks
	private SignUpController signUpController;

	@Mock
	private MemberCommandService memberCommandService;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(signUpController)
			.setControllerAdvice(MemberControllerAdvice.class)
			.build();
	}

	@Test
	@DisplayName("회원가입 성공 테스트")
	void memberRegistrationSuccessTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto();

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isCreated());
		then(memberCommandService).should()
			.signUp(any(SignUpDto.class));
	}

	@ParameterizedTest
	@MethodSource("signUpValidityArgumentsList")
	@DisplayName("회원가입 실패 메시지 확인 테스트: 회원 가입 필수 값 미포함 예외")
	void memberRegistrationValidationTest(String userId, String username, String password,
		String phoneNum, String address) throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto(userId, username, password, phoneNum, address);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트 : 아이디 미포함 실패")
	void invalidUserIdEntryTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto(null, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("userId"))
			.andExpect(jsonPath("$.errors[0].reason").value("아이디는 반드시 입력해야 합니다."));
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트 : 아이디값 사이즈 최소 이하 실패")
	void idValueSizeMinimumOrLessFailureTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto("1", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("userId"))
			.andExpect(jsonPath("$.errors[0].reason").value("아이디 값은 3자리에서 15자리 이하입니다."));
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트 : 아이디값 사이즈 최대 이상 실패")
	void idValueSizeMaximumOrThanFailureTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto("123456789101112222", USERNAME, PASSWORD, PHONE_NUM,
			ADDRESS);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("userId"))
			.andExpect(jsonPath("$.errors[0].reason").value("아이디 값은 3자리에서 15자리 이하입니다."));
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트: 이름 미포함 실패")
	void enteringAnInvalidNameValueTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto(USER_ID, "", PASSWORD, PHONE_NUM, ADDRESS);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("username"))
			.andExpect(jsonPath("$.errors[0].reason").value("이름은 반드시 입력해야 합니다."));
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트 : 핸드폰 번호 미포함 실패")
	void enterANullPhoneNumberTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto(USER_ID, USERNAME, PASSWORD, null, ADDRESS);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("phoneNum"))
			.andExpect(jsonPath("$.errors[0].reason").value("핸드폰 번호는 반드시 입력해야 합니다."));
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트 : 잘못된 핸드폰 번호 등록 실패")
	void enterTheWrongPhoneNumberTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto(USER_ID, USERNAME, PASSWORD, "-0-30-", ADDRESS);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("phoneNum"))
			.andExpect(jsonPath("$.errors[0].reason").value("핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx"));
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트 : 비밀번호 미포함 실패")
	void failedToNotIncludeAddressTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto(USER_ID, USERNAME, "", PHONE_NUM, ADDRESS);

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("password"))
			.andExpect(jsonPath("$.errors[0].reason").value("비밀번호는 반드시 입력해야 합니다."));
	}

	@Test
	@DisplayName("회원가입 실패 메시지 확인 테스트 : 주소 값 미포함 실패")
	void failedToNotIncludePhoneNumTest() throws Exception {
		//given
		SignUpDto request = MemberTestDataFactory.createSignUpDto(USER_ID, USERNAME, PASSWORD, PHONE_NUM, "");

		//when
		ResultActions result = mockMvc.perform(post(SIGNUP_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		//then
		result.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
			.andExpect(jsonPath("$.errors[0].field").value("address"))
			.andExpect(jsonPath("$.errors[0].reason").value("주소는 반드시 입력해야 합니다."));
	}

	static Stream<Arguments> signUpValidityArgumentsList() {
		return Stream.of(
			Arguments.of("", "John", "password", "010-1234-5678", "Seoul"),
			Arguments.of("john", "", "password", "010-1234-5678", "Seoul"),
			Arguments.of("john", "John", "", "010-1234-5678", "Seoul"),
			Arguments.of("john", "John", "password", "", "Seoul"),
			Arguments.of("john", "John", "password", "1234567890", "Seoul"),
			Arguments.of("john", "John", "password", "010-1234-5678", ""),
			Arguments.of(null, "John", "password", "010-1234-5678", "Seoul"),
			Arguments.of("john", null, "password", "010-1234-5678", "Seoul"),
			Arguments.of("john", "John", null, "010-1234-5678", "Seoul"),
			Arguments.of("john", "John", "password", null, "Seoul"),
			Arguments.of("john", "John", "password", "1234567890", "서울")
		);
	}
}
