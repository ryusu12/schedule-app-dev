package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.res.ScheduleResDto;
import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.UnauthorizedException;
import com.example.scheduleappdev.repository.CommentRepository;
import com.example.scheduleappdev.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    // 생성
    public ScheduleResDto createSchedule(User user, String title, String content) {
        Schedule schedule = new Schedule(title, content);
        schedule.setAuthor(user);

        log.info("일정 생성 : scheduleId = {}, author = {}", schedule.getId(), user.getName());
        return createScheduleResDto(scheduleRepository.save(schedule));
    }

    // 조회
    public List<ScheduleResDto> findScheduleList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findAllByOrderByUpdatedDateTimeDesc(pageable)
                .stream().map(this::createScheduleResDto).toList();
    }

    public ScheduleResDto findScheduleById(Long id) {
        Schedule findSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        log.info("일정 조회 : id = {}", id);
        return createScheduleResDto(findSchedule);
    }

    // 수정
    @Transactional
    public ScheduleResDto updateSchedule(Long id, User user, String title, String content) {
        Schedule findSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        checkScheduleAuthor(findSchedule, user);

        findSchedule.updateSchedule(title, content);
        log.info("일정 수정 : id = {}", id);
        return createScheduleResDto(findSchedule);
    }

    // 삭제
    public void deleteScheduleById(Long id, User user) {
        Schedule findSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        checkScheduleAuthor(findSchedule, user);

        log.info("일정 삭제 : id = {}", id);
        scheduleRepository.delete(findSchedule);
    }

    private void checkScheduleAuthor(Schedule schedule, User user) {
        if (!schedule.getAuthor().equals(user)) {
            log.warn("작성자가 일치하지 않습니다.");
            throw new UnauthorizedException("작성자가 일치하지 않습니다.");
        }
    }

    private ScheduleResDto createScheduleResDto(Schedule schedule) {
        Long commentCount = commentRepository.countCommentBySchedule_Id(schedule.getId());
        return new ScheduleResDto(schedule, commentCount);
    }

}