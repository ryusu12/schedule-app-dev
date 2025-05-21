package com.example.scheduleappdev.dto.res;

import com.example.scheduleappdev.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResDto {

    private final Long commentId;
    private final String content;
    private final String author;
    private final Long scheduleId;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

    public CommentResDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor().getName();
        this.scheduleId = comment.getSchedule().getId();
        this.createdDateTime = comment.getCreatedDateTime();
        this.updatedDateTime = comment.getUpdatedDateTime();
    }

}