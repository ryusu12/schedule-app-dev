package com.example.scheduleappdev.dto;

import com.example.scheduleappdev.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResDto {

    private final Long scheduleId;
    private final Long userId;
    private final String todoTitle;
    private final String todoContents;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    public ScheduleResDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.userId = schedule.getUser().getUserId();
        this.todoTitle = schedule.getTodoTitle();
        this.todoContents = schedule.getTodoContents();
        this.createdDate = schedule.getCreatedDate();
        this.updatedDate = schedule.getUpdatedDate();
    }

}