package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.req.CreateCommentReqDto;
import com.example.scheduleappdev.dto.req.UpdateCommentReqDto;
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
    public ResponseEntity<List<CommentResDto>> findCommentList(
            @RequestParam() Long scheduleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<CommentResDto> commentResDtoList = commentService.findCommentList(scheduleId, page, size);
        log.info("댓글 \"{}\"개 조회", commentResDtoList.size());
        return new ResponseEntity<>(commentResDtoList, HttpStatus.OK);
    }

    // 댓글 선택 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentResDto> findCommentById(@PathVariable Long id) {
        CommentResDto commentResDto = commentService.findCommentById(id);
        return new ResponseEntity<>(commentResDto, HttpStatus.OK);
    }

    // 본인 댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResDto> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCommentReqDto reqDto,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        CommentResDto commentResDto = commentService.updateComment(id, findUser, reqDto.getContent());
        return new ResponseEntity<>(commentResDto, HttpStatus.OK);
    }

    // 본인 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(
            @PathVariable Long id,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        commentService.deleteCommentById(id, findUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}