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

import java.util.List;

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

    // 특정 일정에 있는 모든 댓글 조회
    @GetMapping()
    public ResponseEntity<List<CommentResDto>> findCommentList(@RequestParam() Long scheduleId) {
        List<CommentResDto> commentResDtoList = commentService.findCommentList(scheduleId);
        log.info("댓글 \"{}\"개 조회", commentResDtoList.size());
        return new ResponseEntity<>(commentResDtoList, HttpStatus.OK);
    }

    // 댓글 선택 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentResDto> findCommentById(@PathVariable Long id) {
        CommentResDto commentResDto = commentService.findCommentById(id);
        return new ResponseEntity<>(commentResDto, HttpStatus.OK);
    }

}