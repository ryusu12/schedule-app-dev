package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginUserReqDto {

    @Email
    @NotEmpty()
    private final String userEmail;

    @NotEmpty()
    private final String password;

    public LoginUserReqDto(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

}