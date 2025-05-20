package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleReqDto {

    @Size(max = 20)
    @NotEmpty()
    private final String todoTitle;

    @Size(max = 500)
    private final String todoContents;

    public UpdateScheduleReqDto(String todoTitle, String todoContents) {
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }

}