package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.ScheduleResDto;
import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.UnauthorizedException;
import com.example.scheduleappdev.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResDto createSchedule(User user, String todoTitle, String todoContents) {
        Schedule schedule = new Schedule(todoTitle, todoContents);
        schedule.setUser(user);

        log.info("일정 생성 : scheduleId = {}, userName = {}", schedule.getScheduleId(), user.getUserName());
        return new ScheduleResDto(scheduleRepository.save(schedule));
    }

    public List<ScheduleResDto> findAllSchedules() {
        return scheduleRepository.findAll().stream().map(ScheduleResDto::new).toList();
    }

    public ScheduleResDto findScheduleById(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        log.info("일정 조회 : id = {}", id);
        return new ScheduleResDto(findSchedule);
    }

    @Transactional
    public ScheduleResDto updateSchedule(Long id, User user, String todoTitle, String todoContents) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        checkScheduleAuthor(findSchedule, user);

        findSchedule.updateSchedule(todoTitle, todoContents);
        log.info("일정 수정 : id = {}", id);
        return new ScheduleResDto(findSchedule);
    }

    public void deleteScheduleById(Long id, User user) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        checkScheduleAuthor(findSchedule, user);

        log.info("일정 삭제 : id = {}", id);
        scheduleRepository.delete(findSchedule);
    }

    private void checkScheduleAuthor(Schedule schedule, User user) {
        if (!schedule.getUser().getUserName().equals(user.getUserName())) {
            log.warn("작성자가 일치하지 않습니다.");
            throw new UnauthorizedException("작성자가 일치하지 않습니다.");
        }
    }

}