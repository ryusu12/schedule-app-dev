package com.example.scheduleappdev.dto.res;

import com.example.scheduleappdev.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResDto {

    private final Long userId;
    private final String userName;
    private final String userEmail;
    private final String password;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

    public UserResDto(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.userEmail = user.getEmail();
        this.password = user.getPassword();
        this.createdDateTime = user.getCreatedDateTime();
        this.updatedDateTime = user.getUpdatedDateTime();
    }

}