package com.example.scheduleappdev.dto.res.err;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResDto {

    private final Integer status;
    private final String error;
    private final String message;
    private final String timestamp;

    public ErrorResDto(Integer status, String error, String message, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp.toString();
    }

}