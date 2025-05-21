package com.example.scheduleappdev.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentReqDto {

    @NotNull()
    private final Long scheduleId;

    @Size(max = 50)
    @NotEmpty()
    private final String content;

    public CreateCommentReqDto(Long scheduleId, String content) {
        this.scheduleId = scheduleId;
        this.content = content;
    }

}