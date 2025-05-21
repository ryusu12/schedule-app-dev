package com.example.scheduleappdev.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleReqDto {

    @Size(max = 20)
    @NotEmpty()
    private final String title;

    @Size(max = 500)
    private final String content;

    public UpdateScheduleReqDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}