package com.example.scheduleappdev.dto.res.err;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorListResDto {

    private final Integer status;
    private final String error;
    private final List<ErrorDetailDto> message;
    private final String timestamp;

    public ErrorListResDto(Integer status, String error, List<ErrorDetailDto> errorDetailList, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = errorDetailList;
        this.timestamp = timestamp.toString();
    }

}