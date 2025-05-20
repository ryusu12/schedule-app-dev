package com.example.scheduleappdev.service;

import com.example.scheduleappdev.common.Const;
import com.example.scheduleappdev.dto.ScheduleResDto;
import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.UnauthorizedException;
import com.example.scheduleappdev.repository.ScheduleRepository;
import com.example.scheduleappdev.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ScheduleResDto updateSchedule(Long id, String todoTitle, String todoContents, HttpServletRequest req) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        checkScheduleAuthor(findSchedule, req);

        findSchedule.updateSchedule(todoTitle, todoContents);
        log.info("일정 수정 : id = {}", id);
        return new ScheduleResDto(findSchedule);
    }

    public void deleteScheduleById(Long id, HttpServletRequest req) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        checkScheduleAuthor(findSchedule, req);

        log.info("일정 삭제 : id = {}", id);
        scheduleRepository.delete(findSchedule);
    }

    public void checkScheduleAuthor(Schedule schedule, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserResDto loginUser = (UserResDto) session.getAttribute(Const.LOGIN_USER);
            if (!schedule.getUser().getUserName().equals(loginUser.getUserName())) {
                throw new UnauthorizedException("작성자가 일치하지 않습니다.");
            }
        }
    }

}