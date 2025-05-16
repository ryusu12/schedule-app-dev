package com.example.scheduleappdev.dto;

import lombok.Getter;

@Getter
public class UpdateUserEmailReqDto {

    private final String userName;
    private final String userEmail;

    public UpdateUserEmailReqDto(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

}