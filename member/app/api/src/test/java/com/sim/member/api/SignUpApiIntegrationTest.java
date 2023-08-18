package com.sim.member.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.sim.member.dto.request.MemberCreateRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

/**
 * MemberController.java 회원 컨트롤러 테스트
 *
 * @author sgh
 * @since 2023.03.17
 */
class SignUpApiIntegrationTest extends ControllerTestSupport {

  private final static String USER_ID = "test";
  private final static String USERNAME = "이순신";
  private final static String PHONE_NUM = "010-1111-9999";
  private final static String ADDRESS = "서울시 마포구 창천동";
  private final static String PASSWORD = "password";
  private final static String SIGNUP_URL = "/api/signup";

  private final Gson gson = new Gson();

  private final int USER_ID_MIN_SIZE = 3;
  private final int USER_ID_MAX_SIZE = 15;

  @Test
  @DisplayName("회원가입 성공")
  void memberRegistrationSuccessTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(USER_ID, USERNAME);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isCreated());
  }

  static Stream<Arguments> signUpValidityArgumentsList() {
    return Stream.of(Arguments.of("", "John", "password", "010-1234-5678", "Seoul"),
        Arguments.of("john", "", "password", "010-1234-5678", "Seoul"),
        Arguments.of("john", "John", "", "010-1234-5678", "Seoul"),
        Arguments.of("john", "John", "password", "", "Seoul"),
        Arguments.of("john", "John", "password", "1234567890", "Seoul"),
        Arguments.of("john", "John", "password", "010-1234-5678", ""),
        Arguments.of(null, "John", "password", "010-1234-5678", "Seoul"),
        Arguments.of("john", null, "password", "010-1234-5678", "Seoul"),
        Arguments.of("john", "John", null, "010-1234-5678", "Seoul"),
        Arguments.of("john", "John", "password", null, "Seoul"),
        Arguments.of("john", "John", "password", "1234567890", "서울"));
  }

  @ParameterizedTest
  @MethodSource("signUpValidityArgumentsList")
  @DisplayName("회원 가입시 잘못된 입력 값 등록시 실패합니다.")
  void memberRegistrationValidationTest(String userId, String username, String password,
      String phoneNum, String address) throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(userId, username, password,
        phoneNum, address);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("userId는 포함되지 않으면 에러 메시지를 반환합니다.")
  void invalidUserIdEntryTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(null, USERNAME, PASSWORD,
        PHONE_NUM, ADDRESS);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("userId"))
        .andExpect(jsonPath("$.errors[0].reason").value("아이디는 반드시 입력해야 합니다."));
  }

  @Test
  @DisplayName("userId 값의 길이는 허용 범위 내여야 합니다.")
  void idValueSizeMinimumFailureTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(
        "a".repeat(USER_ID_MIN_SIZE - 1), USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("userId"))
        .andExpect(jsonPath("$.errors[0].reason").value("아이디 값은 3자리에서 15자리 이하입니다."));
  }

  @Test
  @DisplayName("userId 값의 길이는 허용 범위내여야 합니다.")
  void idValueSizeLessFailureTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(
        "a".repeat(USER_ID_MAX_SIZE + 1), USERNAME, PASSWORD, PHONE_NUM, ADDRESS);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("userId"))
        .andExpect(jsonPath("$.errors[0].reason").value("아이디 값은 3자리에서 15자리 이하입니다."));
  }

  @Test
  @DisplayName("회원 가입할 때, username 은 필수 값이며 없을시 에러 메시지를 반환합니다.")
  void enteringAnInvalidNameValueTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(USER_ID, "", PASSWORD,
        PHONE_NUM, ADDRESS);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("username"))
        .andExpect(jsonPath("$.errors[0].reason").value("이름은 반드시 입력해야 합니다."));
  }

  @Test
  @DisplayName("회원 가입할 때, 핸드폰 번호는 필수 값이며 없을시 에러 메시지를 반환합니다.")
  void enterANullPhoneNumberTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(USER_ID, USERNAME, PASSWORD,
        null, ADDRESS);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("phoneNum"))
        .andExpect(jsonPath("$.errors[0].reason").value("핸드폰 번호는 반드시 입력해야 합니다."));
  }

  @Test
  @DisplayName("회원 가입할 때 양식에 맞지 않는 핸드폰 번호를 기입시 에러 메시지를 반환합니다.")
  void enterTheWrongPhoneNumberTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(USER_ID, USERNAME, PASSWORD,
        "-0-30-", ADDRESS);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("phoneNum"))
        .andExpect(jsonPath("$.errors[0].reason").value("핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx"));
  }

  @Test
  @DisplayName("회원가입할 때 비밀번호는 필수 값입니다. 미기입시 에러 메시지를 반환합니다.")
  void failedToNotIncludeAddressTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(USER_ID, USERNAME, "",
        PHONE_NUM, ADDRESS);

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("password"))
        .andExpect(jsonPath("$.errors[0].reason").value("비밀번호는 반드시 입력해야 합니다."));
  }

  @Test
  @DisplayName("회원가입할 때 주소는 필수 값입니다. 미기입시 에러 메시지를 반환합니다.")
  void failedToNotIncludePhoneNumTest() throws Exception {
    //given
    MemberCreateRequest memberCreateRequest = createMemberCreateRequest(USER_ID, USERNAME, PASSWORD,
        PHONE_NUM, "");

    //when
    ResultActions result = mockMvc.perform(post(SIGNUP_URL).contentType(MediaType.APPLICATION_JSON)
        .content(gson.toJson(memberCreateRequest)));

    //then
    result.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("입력 값이 올바르지 않습니다."))
        .andExpect(jsonPath("$.errors[0].field").value("address"))
        .andExpect(jsonPath("$.errors[0].reason").value("주소는 반드시 입력해야 합니다."));
  }


  private MemberCreateRequest createMemberCreateRequest(String userId, String username) {
    return MemberCreateRequest.builder()
        .userId(userId)
        .username(username)
        .phoneNum(PHONE_NUM)
        .password(PASSWORD)
        .address(ADDRESS)
        .build();
  }

  private MemberCreateRequest createMemberCreateRequest(String userId, String username,
      String password, String phoneNum, String address) {
    return MemberCreateRequest.builder().userId(userId).username(username).phoneNum(phoneNum)
        .password(password).address(address).build();
  }
}
