package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.req.CreateScheduleReqDto;
import com.example.scheduleappdev.dto.res.ScheduleResDto;
import com.example.scheduleappdev.dto.req.UpdateScheduleReqDto;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.service.ScheduleService;
import com.example.scheduleappdev.service.SessionService;
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
    private final SessionService sessionService;

    // 일정 생성
    @PostMapping()
    public ResponseEntity<ScheduleResDto> createSchedule(
            @Valid @RequestBody CreateScheduleReqDto reqDto,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        ScheduleResDto scheduleResDto = scheduleService.createSchedule(findUser, reqDto.getTitle(), reqDto.getContent());
        return new ResponseEntity<>(scheduleResDto, HttpStatus.CREATED);
    }

    // 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResDto>> findScheduleList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ScheduleResDto> scheduleResDtoList = scheduleService.findScheduleList(page, size);
        log.info("일정 \"{}\"개 조회", scheduleResDtoList.size());
        return new ResponseEntity<>(scheduleResDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResDto> findScheduleById(@PathVariable Long id) {
        ScheduleResDto scheduleResDto = scheduleService.findScheduleById(id);
        return new ResponseEntity<>(scheduleResDto, HttpStatus.OK);
    }

    // 본인 일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleReqDto reqDto,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        ScheduleResDto scheduleResDto = scheduleService.updateSchedule(id, findUser, reqDto.getTitle(), reqDto.getContent());
        return new ResponseEntity<>(scheduleResDto, HttpStatus.OK);
    }

    // 본인 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(
            @PathVariable Long id,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        scheduleService.deleteScheduleById(id, findUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}