package com.example.scheduleappdev.dto;

import lombok.Getter;

@Getter
public class UpdateUserPasswordReqDto {

    private final String oldPassword;
    private final String newPassword;

    public UpdateUserPasswordReqDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}