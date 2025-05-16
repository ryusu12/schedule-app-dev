package com.example.scheduleappdev.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleReqDto {

    private final String authorName;
    private final String todoTitle;
    private final String todoContents;

    public UpdateScheduleReqDto(String authorName, String todoTitle, String todoContents) {
        this.authorName = authorName;
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }

}