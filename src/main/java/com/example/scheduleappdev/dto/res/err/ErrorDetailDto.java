package com.example.scheduleappdev.dto.res.err;

import lombok.Getter;

@Getter
public class ErrorDetailDto {

    private final String field;
    private final String message;

    public ErrorDetailDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

}