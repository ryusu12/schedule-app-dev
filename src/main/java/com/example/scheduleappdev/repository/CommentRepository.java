package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}