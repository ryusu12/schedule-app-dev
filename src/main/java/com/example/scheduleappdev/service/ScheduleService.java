package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.ScheduleResDto;
import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.repository.ScheduleRepository;
import com.example.scheduleappdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleResDto createSchedule(String authorName, String todoTitle, String todoContents) {
        User findUser = userRepository.findUserByUserNameOrElseThrow(authorName);

        // 일정에 유저 연결
        Schedule schedule = new Schedule(todoTitle, todoContents);
        schedule.setUser(findUser);

        log.info("일정 생성 : scheduleId = {}, userName = {}", schedule.getScheduleId(), findUser.getUserName());
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
    public ScheduleResDto updateSchedule(Long id, String authorName, String todoTitle, String todoContents) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        // 이름으로 비교
        if (!findSchedule.getUser().getUserName().equals(authorName)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "작성자가 일치하지 않습니다.");
        }
        findSchedule.updateSchedule(todoTitle, todoContents);
        log.info("일정 수정 : id = {}", id);
        return new ScheduleResDto(findSchedule);
    }

    public void deleteScheduleById(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        log.info("일정 삭제 : id = {}", id);
        scheduleRepository.delete(findSchedule);
    }

}