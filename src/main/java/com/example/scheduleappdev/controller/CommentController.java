package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.req.CreateCommentReqDto;
import com.example.scheduleappdev.dto.res.CommentResDto;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.service.CommentService;
import com.example.scheduleappdev.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final SessionService sessionService;

    // 댓글 생성
    @PostMapping()
    public ResponseEntity<CommentResDto> createComment(
            @Valid @RequestBody CreateCommentReqDto reqDto,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        CommentResDto commentResDto = commentService.createComment(findUser, reqDto.getScheduleId(), reqDto.getContent());
        return new ResponseEntity<>(commentResDto, HttpStatus.CREATED);
    }

}