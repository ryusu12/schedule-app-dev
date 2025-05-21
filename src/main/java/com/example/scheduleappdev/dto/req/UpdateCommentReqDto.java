package com.example.scheduleappdev.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentReqDto {

    @Size(max = 50)
    @NotEmpty()
    private final String content;

    public UpdateCommentReqDto(String content) {
        this.content = content;
    }

}