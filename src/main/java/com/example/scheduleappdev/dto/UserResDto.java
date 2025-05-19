package com.example.scheduleappdev.dto;

import com.example.scheduleappdev.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResDto {

    private final Long userId;
    private final String userName;
    private final String userEmail;
    private final String password;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public UserResDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.password = user.getPassword();
        this.createdDate = user.getCreatedDate();
        this.updatedDate = user.getUpdatedDate();
    }

}