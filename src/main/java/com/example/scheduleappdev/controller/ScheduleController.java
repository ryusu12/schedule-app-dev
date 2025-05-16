package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.CreateScheduleReqDto;
import com.example.scheduleappdev.dto.ScheduleResDto;
import com.example.scheduleappdev.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ScheduleResDto>> findAllSchedules() {
        List<ScheduleResDto> scheduleResDtoList = scheduleService.findAllSchedules();
        return new ResponseEntity<>(scheduleResDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResDto> findScheduleById(@PathVariable Long id) {
        ScheduleResDto scheduleResDto = scheduleService.findScheduleById(id);
        return new ResponseEntity<>(scheduleResDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Long id) {
        scheduleService.deleteScheduleById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}