package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class DeleteUserReqDto {

    @NotEmpty()
    private final String password;

    public DeleteUserReqDto(String password) {
        this.password = password;
    }

}