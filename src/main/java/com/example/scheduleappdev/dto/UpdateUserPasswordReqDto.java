package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateUserPasswordReqDto {

    @NotEmpty()
    private final String oldPassword;

    @NotEmpty()
    private final String newPassword;

    public UpdateUserPasswordReqDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}