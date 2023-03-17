package com.reservation.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SignUpDto.java
 * 회원 가입 DTO
 *
 * @author sgh
 * @since 2023.03.17
 */
@Getter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "아이디는 반드시 입력해야 합니다.")
    @Size(min = 3, max = 15, message = "아이디 값은 3자리에서 15자리 이하입니다.")
    private String userId;

    @NotBlank(message = "이름은 반드시 입력해야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
    private String password;

    @NotBlank(message = "핸드폰 번호는 반드시 입력해야 합니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. ex) 010-xxxx-xxxx")
    private String phoneNum;

    @NotBlank(message = "주소는 반드시 입력해야 합니다.")
    private String address;

    private SignUpRequest(String userId, String username, String password, String phoneNum, String address) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    public static SignUpRequest of(final String userId, final String username, final String password,
                                   final String phoneNum, final String address) {
        return new SignUpRequest(userId, username, password, phoneNum, address);
    }
}
