package com.example.scheduleappdev.dto;

import lombok.Getter;

@Getter
public class CreateUserReqDto {
    private final String userName;
    private final String userEmail;

    public CreateUserReqDto(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }
}