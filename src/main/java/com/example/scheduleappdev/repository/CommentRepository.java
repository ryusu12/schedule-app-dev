package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.Comment;
import com.example.scheduleappdev.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentBySchedule_Id(Long scheduleId);

    default Comment findCommentByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("댓글이 존재하지 않습니다. id = " + id));
    }
}