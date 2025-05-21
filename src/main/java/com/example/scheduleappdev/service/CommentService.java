package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.res.CommentResDto;
import com.example.scheduleappdev.entity.Comment;
import com.example.scheduleappdev.entity.Schedule;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.repository.CommentRepository;
import com.example.scheduleappdev.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResDto createComment(User user, Long scheduleId, String content) {
        Comment comment = new Comment(content);
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);

        comment.setAuthor(user);
        comment.setSchedule(schedule);

        log.info("댓글 생성 : scheduleId = {}, author = {}", schedule.getId(), user.getName());
        return new CommentResDto(commentRepository.save(comment));
    }

}