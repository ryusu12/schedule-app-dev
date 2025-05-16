package com.example.scheduleappdev.dto;

import lombok.Getter;

@Getter
public class CreateScheduleReqDto {

    private final String authorName;
    private final String todoTitle;
    private final String todoContents;

    public CreateScheduleReqDto(String authorName, String todoTitle, String todoContents) {
        this.authorName = authorName;
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }

}