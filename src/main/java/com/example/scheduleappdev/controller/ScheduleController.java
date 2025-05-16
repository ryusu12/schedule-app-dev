package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.CreateScheduleReqDto;
import com.example.scheduleappdev.dto.ScheduleResDto;
import com.example.scheduleappdev.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping()
    public ResponseEntity<ScheduleResDto> createSchedule(@RequestBody CreateScheduleReqDto reqDto) {
        ScheduleResDto scheduleResDto = scheduleService.createSchedule(reqDto.getAuthorName(), reqDto.getTodoTitle(), reqDto.getTodoContents());
        return new ResponseEntity<>(scheduleResDto, HttpStatus.CREATED);
    }
}