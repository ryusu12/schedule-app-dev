package com.example.scheduleappdev.dto;

import lombok.Getter;

@Getter
public class LoginUserReqDto {

    private final String userEmail;
    private final String password;

    public LoginUserReqDto(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

}