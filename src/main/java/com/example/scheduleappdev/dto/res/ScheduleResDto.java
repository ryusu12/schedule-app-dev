package com.example.scheduleappdev.dto.res;

import com.example.scheduleappdev.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResDto {

    private final Long scheduleId;
    private final String author;
    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

    public ScheduleResDto(Schedule schedule, Long commentCount) {
        this.scheduleId = schedule.getId();
        this.author = schedule.getAuthor().getName();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.commentCount = commentCount;
        this.createdDateTime = schedule.getCreatedDateTime();
        this.updatedDateTime = schedule.getUpdatedDateTime();
    }

}