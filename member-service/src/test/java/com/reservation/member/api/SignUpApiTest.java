package com.reservation.member.api;

import com.google.gson.Gson;
import com.reservation.member.application.MemberCreator;
import com.reservation.member.application.MemberService;
import com.reservation.member.dto.SignUpRequest;
import com.reservation.member.exception.MemberControllerAdvice;
import com.reservation.member.factory.SignUpFactory;
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

import java.util.stream.Stream;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MemberController.java
 * 회원 컨트롤러 테스트
 *
 * @author sgh
 * @since 2023.03.17
 */
@ExtendWith(MockitoExtension.class)
public class SignUpApiTest {
    private final static String USER_ID = "firstUser";
    private final static String USERNAME = "이순신";
    private final static String PASSWORD = "password";
    private final static String PHONE_NUM = "010-8988-9999";
    private final static String ADDRESS = "경기도 한국군 한국리";
    private final static String SIGNUP_URL = "/api/signup";
    
    private MockMvc mockMvc;
    private Gson gson;

    @InjectMocks
    private SignUpController signUpController;

    @Mock
    private MemberService memberService;

    @BeforeEach
    void beforeEach() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(signUpController)
                .setControllerAdvice(MemberControllerAdvice.class)
                .build();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void memberRegistrationSuccessTest() throws Exception {
        //given
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

        //when
        ResultActions result = mockMvc.perform(post(SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)));

        //then
        result.andExpect(status().isCreated());
        then(memberService).should()
                .signUp(any(SignUpRequest.class));
    }

    @ParameterizedTest
    @MethodSource("signUpValidityArgumentsList")
    @DisplayName("회원가입 실패 메시지 확인 테스트: 회원 가입 필수 값 미포함 예외")
    void memberRegistrationValidationTest(String userId, String username, String password,
                                          String phoneNum, String address) throws Exception {
        //given
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(userId, username, password, phoneNum, address);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(null, USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성("1", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성("123456789101112222", USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, "", PASSWORD, PHONE_NUM, ADDRESS);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, USERNAME, PASSWORD, null, ADDRESS);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, USERNAME, PASSWORD, "-0-30-", ADDRESS);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, USERNAME, "", PHONE_NUM, ADDRESS);

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
        SignUpRequest request = SignUpFactory.회원가입_DTO_생성(USER_ID, USERNAME, PASSWORD, PHONE_NUM, "");

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
