package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.CreateScheduleReqDto;
import com.example.scheduleappdev.dto.ScheduleResDto;
import com.example.scheduleappdev.dto.UpdateScheduleReqDto;
import com.example.scheduleappdev.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping()
    public ResponseEntity<ScheduleResDto> createSchedule(@Valid @RequestBody CreateScheduleReqDto reqDto) {
        ScheduleResDto scheduleResDto = scheduleService.createSchedule(reqDto.getAuthorName(), reqDto.getTodoTitle(), reqDto.getTodoContents());
        return new ResponseEntity<>(scheduleResDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResDto>> findAllSchedules() {
        List<ScheduleResDto> scheduleResDtoList = scheduleService.findAllSchedules();
        log.info("일정 \"{}\"개 조회", scheduleResDtoList.size());
        return new ResponseEntity<>(scheduleResDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResDto> findScheduleById(@PathVariable Long id) {
        ScheduleResDto scheduleResDto = scheduleService.findScheduleById(id);
        return new ResponseEntity<>(scheduleResDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleReqDto reqDto,
            HttpServletRequest req
    ) {
        ScheduleResDto scheduleResDto = scheduleService.updateSchedule(id, reqDto.getTodoTitle(), reqDto.getTodoContents(), req);
        return new ResponseEntity<>(scheduleResDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(
            @PathVariable Long id,
            HttpServletRequest req
    ) {
        scheduleService.deleteScheduleById(id, req);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}