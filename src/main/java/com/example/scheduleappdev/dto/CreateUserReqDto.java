package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateUserReqDto {

    @NotEmpty()
    private final String userName;

    @Email
    @NotEmpty()
    private final String userEmail;

    @NotEmpty()
    private final String password;

    public CreateUserReqDto(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }

}