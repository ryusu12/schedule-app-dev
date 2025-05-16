package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.ScheduleResDto;
import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResDto createSchedule(String authorName, String todoTitle, String todoContents) {
        Schedule schedule = new Schedule(authorName, todoTitle, todoContents);
        Schedule newSchedule = scheduleRepository.save(schedule);
        return new ScheduleResDto(newSchedule);
    }

}