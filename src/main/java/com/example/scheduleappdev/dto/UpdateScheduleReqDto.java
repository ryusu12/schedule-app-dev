package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateScheduleReqDto {
    @NotEmpty()
    private final String todoTitle;

    private final String todoContents;

    public UpdateScheduleReqDto(String todoTitle, String todoContents) {
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }

}