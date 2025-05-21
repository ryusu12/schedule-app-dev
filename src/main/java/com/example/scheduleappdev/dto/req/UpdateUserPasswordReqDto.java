package com.example.scheduleappdev.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserPasswordReqDto {

    @NotEmpty()
    @Pattern(regexp = "^.*(?=^.{4,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 사용가능하며, 길이는 4 이상 20 이하 입니다.")
    private final String oldPassword;

    @NotEmpty()
    @Pattern(regexp = "^.*(?=^.{4,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 영문자, 숫자, 특수문자만 사용가능하며, 길이는 4 이상 20 이하 입니다.")
    private final String newPassword;

    public UpdateUserPasswordReqDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}