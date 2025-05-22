package com.example.scheduleappdev.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class ErrorListResDto {

    private final Integer status;
    private final String error;
    private final List<Map<String, String>> message;
    private final String timestamp;

    public ErrorListResDto(Integer status, String error, List<Map<String, String>> message, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp.toString();
    }

}