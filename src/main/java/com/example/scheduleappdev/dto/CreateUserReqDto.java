package com.example.scheduleappdev.dto;

import lombok.Getter;

@Getter
public class CreateUserReqDto {

    private final String userName;
    private final String userEmail;
    private final String password;

    public CreateUserReqDto(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }

}