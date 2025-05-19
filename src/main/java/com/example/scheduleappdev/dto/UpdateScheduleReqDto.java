package com.example.scheduleappdev.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleReqDto {

    @NotEmpty()
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,10}$", message = "이름은 영문자, 한글, 숫자만 사용가능하며, 길이는 1 이상 10 이하 입니다.")
    private final String authorName;

    @Size(max = 20)
    @NotEmpty()
    private final String todoTitle;

    @Size(max = 500)
    private final String todoContents;

    public UpdateScheduleReqDto(String authorName, String todoTitle, String todoContents) {
        this.authorName = authorName;
        this.todoTitle = todoTitle;
        this.todoContents = todoContents;
    }

}