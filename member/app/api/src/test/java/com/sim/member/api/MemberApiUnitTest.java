package com.sim.member.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.sim.member.dto.request.MemberUpdateRequest;
import com.sim.member.error.ErrorCode;
import com.sim.member.error.MemberControllerAdvice;
import com.sim.member.memberdomain.dto.MemberDto;
import com.sim.member.memberdomain.dto.MemberPerformanceDto;
import com.sim.member.memberdomain.error.InvalidUserIdException;
import com.sim.member.memberdomain.error.MemberNotFoundException;
import com.sim.member.memberdomain.service.MemberCommandService;
import com.sim.member.memberdomain.service.MemberQueryService;
import java.util.ArrayList;
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

/**
 * MemberApiTest.java 회원 정보 API Test
 *
 * @author sgh
 * @since 2023.03.23
 */
@ExtendWith(MockitoExtension.class)
public class MemberApiUnitTest {

  private final static String MEMBER_API_URL = "/api/members";
  private final static String USER_ID = "test";
  private final static Long MEMBER_ID = 1L;
  private final static String USERNAME = "이순신";
  private final static String PHONE_NUM = "010-1111-9999";
  private final static String ADDRESS = "서울시 마포구 창천동";

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
  @DisplayName("Member ID로 회원 정보 조회 API")
  class MemberLookupApiTestByMemberIdTest {

    private final String MEMBER_SEARCH_URL = MEMBER_API_URL + "/" + MEMBER_ID;

    @Test
    @DisplayName("등록되지 않은 Member ID로 조회시 실패한다.")
    void return400StatusCodeWhenNoRegisteredMember() throws Exception {
      // given
      given(memberQueryService.findMemberById(MEMBER_ID)).willThrow(MemberNotFoundException.class);

      // when, then
      mockMvc.perform(get(MEMBER_SEARCH_URL))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("등록되지 않은 Member ID로 조회시 오류 메시지를 반환한다.")
    void checkUnregisteredMemberErrorMessage() throws Exception {
      // given
      given(memberQueryService.findMemberById(MEMBER_ID)).willThrow(MemberNotFoundException.class);

      // when, then
      mockMvc.perform(get(MEMBER_SEARCH_URL))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("등록된 Member ID로 회원 정보를 조회할 수 있다.")
    void memberInquirySuccess200StatusCodeReturned() throws Exception {
      // given
      given(memberQueryService.findMemberById(MEMBER_ID)).willReturn(
          createMemberDto(MEMBER_ID, USER_ID));

      // when then
      mockMvc.perform(get(MEMBER_SEARCH_URL)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("등록된 Member ID로 회원 정보를 조회할 수 있다. 응답값 확인")
    void checkMemberInquirySuccessResponseValue() throws Exception {
      // given
      MemberDto memberDto = createMemberDto(MEMBER_ID, USER_ID);
      given(memberQueryService.findMemberById(MEMBER_ID)).willReturn(memberDto);

      // when
      ResultActions result = mockMvc.perform(get(MEMBER_SEARCH_URL));

      // then
      result.andExpect(jsonPath("$.userId").value(memberDto.getUserId()))
          .andExpect(jsonPath("$.phoneNum").value(memberDto.getPhoneNum()))
          .andExpect(jsonPath("$.username").value(memberDto.getUsername()));
    }

  }

  @Nested
  @DisplayName("User ID로 회원 정보 조회 API")
  class MemberLookupAPITestByUserIDTest {

    private final String USER_PARAM_KEY = "?userId=";

    @Test
    @DisplayName("userId로 회원 정보 조회 성공, 응답값 확인")
    void memberInquirySuccessTest() throws Exception {
      //given
      MemberDto memberDto = createMemberDto(MEMBER_ID, USER_ID);
      given(memberQueryService.findMemberByUserId(USER_ID)).willReturn(memberDto);

      // when, then
      mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
          .andExpect(jsonPath("$.userId", is(USER_ID)))
          .andExpect(jsonPath("$.phoneNum", is(PHONE_NUM)))
          .andExpect(jsonPath("$.username", is(USERNAME)))
          .andExpect(jsonPath("$.address", is(ADDRESS)));
    }

    @Test
    @DisplayName("userId로 회원 정보 조회 성공한다.")
    void memberInquiry200CodeVerificationTest() throws Exception {
      // given
      MemberDto memberDto = createMemberDto(MEMBER_ID, USER_ID);
      given(memberQueryService.findMemberByUserId(USER_ID)).willReturn(memberDto);

      // when, then
      mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
          .andExpect(status().isOk());
    }

    @Test
    @DisplayName("등록되지 않은 userId로 조회시 실패한다.")
    void noMatchingUserIdTestFailed() throws Exception {
      // given
      given(memberQueryService.findMemberByUserId(USER_ID)).willThrow(InvalidUserIdException.class);

      // when, then
      mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("등록되지 않은 userId로 조회시 오류메시지를 반환한다.")
    void noMatchingLoginIDReturnErrorMessage() throws Exception {
      // given
      given(memberQueryService.findMemberByUserId(USER_ID)).willThrow(InvalidUserIdException.class);

      // when, then
      mockMvc.perform(get(MEMBER_API_URL + USER_PARAM_KEY + USER_ID))
          .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_USER_ID_VALUE.getMessage()));
    }
  }

  @Nested
  @DisplayName("회원 정보 수정 API")
  class MemberInformationModificationAPITest {

    @Test
    @DisplayName("등록되지 않은 userId로 수정 요청하면 실패한다.")
    void putApiNoMatchingUserIdTestFailed() throws Exception {
      // given
      given(memberCommandService.updateMemberInfo(any(), any())).willThrow(
          InvalidUserIdException.class);
      MemberUpdateRequest memberUpdateRequest = createUpdateMemberRequest(USER_ID, USERNAME);

      // when, then
      mockMvc.perform(put(MEMBER_API_URL + "/" + USER_ID)
              .contentType(MediaType.APPLICATION_JSON)
              .content(gson.toJson(memberUpdateRequest)))
          .andExpect(status().isBadRequest());
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

    @ParameterizedTest
    @MethodSource("updateValidityArgumentsList")
    @DisplayName("잘못된 값으로 수정 요청을 하면 실패한다.")
    void invalidCorrectionRequestValueExceptionTest(String userId, String phoneNum, String username,
        String address) throws Exception {
      //when
      MemberUpdateRequest memberUpdateRequest = createUpdateMemberRequest(userId, phoneNum,
          username, address);

      //then
      mockMvc.perform(put(MEMBER_API_URL + "/" + USER_ID)
              .contentType(MediaType.APPLICATION_JSON)
              .content(gson.toJson(memberUpdateRequest)))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 정보 수정을 성공한다.")
    void memberInformationModificationSuccessTest() throws Exception {
      // given
      MemberDto memberDto = createMemberDto(MEMBER_ID, USER_ID);
      MemberUpdateRequest memberUpdateRequest = createUpdateMemberRequest(USER_ID, USERNAME);
      given(memberCommandService.updateMemberInfo(any(), any())).willReturn(memberDto);

      // when, then
      mockMvc.perform(put(MEMBER_API_URL + "/" + USER_ID)
              .contentType(MediaType.APPLICATION_JSON)
              .content(gson.toJson(memberUpdateRequest)))
          .andExpect(status().isOk());
    }

    private MemberUpdateRequest createUpdateMemberRequest(String userId, String userName) {
      return MemberUpdateRequest.of(userId, userName, PHONE_NUM, ADDRESS);
    }

    private MemberUpdateRequest createUpdateMemberRequest(String userId, String username,
        String phoneNum, String address) {
      return MemberUpdateRequest.of(userId, username, phoneNum, address);
    }
  }

  @Nested
  @DisplayName("member Id로 등록된 공연 정보 조회")
  class SearchPerformanceRegisteredByUserIdTest {

    private static final String VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL =
        "/api/members/" + MEMBER_ID + "/performances";

    @Test
    @DisplayName("memberId로 등록된 공연 정보 조회에 성공한다.")
    void registeredPerformanceSearchSuccess() throws Exception {
      //given
      MemberPerformanceDto memberPerformanceDto = createMemberPerformanceDto(USER_ID,
          USERNAME);
      given(memberQueryService.selectPerformancesById(MEMBER_ID)).willReturn(memberPerformanceDto);

      //when
      ResultActions result = mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
          .andExpect(status().isOk());

      //then
      result.andExpect(jsonPath("$.userId").value(USER_ID))
          .andExpect(jsonPath("$.userName").value(USERNAME));
    }

    @Test
    @DisplayName("등록되지 않은 memberId로 공연 조회시 실패한다.")
    void returnUnregisteredMember400Code() throws Exception {
      // given
      given(memberQueryService.selectPerformancesById(MEMBER_ID)).willThrow(
          MemberNotFoundException.class);

      // when, then
      mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
          .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("등록되지 앟은 memberId로 공연 조회시 에러 메시지를 반환한다.")
    void returnErrorMessageWhenThereIsNoRegisteredMember() throws Exception {
      // given
      given(memberQueryService.selectPerformancesById(MEMBER_ID)).willThrow(
          MemberNotFoundException.class);

      // when, then
      mockMvc.perform(get(VIEW_PERFORMANCES_REGISTERED_BY_MEMBERS_URL))
          .andExpect(jsonPath("$.message").value(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
    }

    private MemberPerformanceDto createMemberPerformanceDto(String userId, String username) {
      return MemberPerformanceDto.builder()
          .userId(userId)
          .userName(username)
          .performances(new ArrayList<>())
          .build();
    }

  }

  private static MemberDto createMemberDto(Long memberId, String userId) {
    return MemberDto.builder()
        .id(memberId)
        .userId(userId)
        .username(USERNAME)
        .phoneNum(PHONE_NUM)
        .address(ADDRESS)
        .build();
  }

}
