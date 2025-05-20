package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CreateScheduleReqDto {

    @NotEmpty()
    private final String authorName;

    @NotEmpty()
    private final String todoTitle;

    private final String todoContents;

    public CreateScheduleReqDto(String authorName, String todoTitle, String todoContents) {
        this.authorName = authorName;
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }

}