package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.res.CommentResDto;
import com.example.scheduleappdev.entity.Comment;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // 생성
    public CommentResDto createComment(User user, Long scheduleId, String content) {
        Comment comment = new Comment(content);
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(scheduleId);

        comment.setAuthor(user);
        comment.setSchedule(schedule);

        log.info("댓글 생성 : scheduleId = {}, author = {}", schedule.getId(), user.getName());
        return new CommentResDto(commentRepository.save(comment));
    }

    // 조회
    public List<CommentResDto> findCommentList(Long scheduleId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findCommentBySchedule_IdOrderByUpdatedDateTimeDesc(scheduleId, pageable)
                .stream().map(CommentResDto::new).toList();
    }

    public CommentResDto findCommentById(Long id) {
        Comment findComment = commentRepository.findCommentByIdOrElseThrow(id);
        log.info("댓글 조회 : id = {}", id);
        return new CommentResDto(findComment);
    }

    // 수정
    @Transactional
    public CommentResDto updateComment(Long id, User user, String content) {
        Comment findComment = commentRepository.findCommentByIdOrElseThrow(id);
        checkCommentAuthor(findComment, user);

        findComment.updateComment(content);
        log.info("댓글 수정 : id = {}", id);
        return new CommentResDto(findComment);
    }

    // 삭제
    public void deleteCommentById(Long id, User user) {
        Comment findComment = commentRepository.findCommentByIdOrElseThrow(id);
        checkCommentAuthor(findComment, user);

        log.info("댓글 삭제 : id = {}", id);
        commentRepository.delete(findComment);
    }

    private void checkCommentAuthor(Comment comment, User user) {
        if (!comment.getAuthor().equals(user)) {
            log.warn("작성자가 일치하지 않습니다.");
            throw new UnauthorizedException("작성자가 일치하지 않습니다.");
        }
    }

}