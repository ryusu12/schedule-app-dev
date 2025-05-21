package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.Comment;
import com.example.scheduleappdev.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findCommentByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("댓글이 존재하지 않습니다. id = " + id));
    }

    Long countCommentBySchedule_Id(Long scheduleId);

    List<Comment> findCommentBySchedule_IdOrderByUpdatedDateTimeDesc(Long scheduleId, Pageable pageable);

}