package com.example.scheduleappdev.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginUserReqDto {

    @NotEmpty()
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
            message = "이메일 형식에 맞아야 합니다.")
    private final String userEmail;

    @NotEmpty()
    @Pattern(regexp = "^.*(?=^.{4,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 사용가능하며, 길이는 4 이상 20 이하 입니다.")
    private final String password;

    public LoginUserReqDto(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

}