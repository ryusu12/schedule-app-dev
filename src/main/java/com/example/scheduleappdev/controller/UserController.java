package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.*;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.service.SessionService;
import com.example.scheduleappdev.service.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping("/signup")
    public ResponseEntity<UserResDto> createUser(@Valid @RequestBody CreateUserReqDto reqDto) {
        UserResDto userResDto = userService.createUser(reqDto.getUserName(), reqDto.getUserEmail(), reqDto.getPassword());
        return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginUserReqDto reqDto,
            HttpServletRequest req
    ) {
        UserResDto userResDto = userService.login(reqDto.getUserEmail(), reqDto.getPassword());
        sessionService.makeSession(req, userResDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest req) {
        sessionService.logout(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResDto>> findAllUsers() {
        List<UserResDto> userResDtoList = userService.findAllUsers();
        log.info("유저 \"{}\"명 조회", userResDtoList.size());
        return new ResponseEntity<>(userResDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResDto> findUserById(@PathVariable Long id) {
        UserResDto userResDto = userService.findUserById(id);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<UserResDto> updateUserPassword(
            @Valid @RequestBody UpdateUserPasswordReqDto reqDto,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        UserResDto userResDto = userService.updateUserPassword(findUser, reqDto.getOldPassword(), reqDto.getNewPassword());
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody DeleteUserReqDto reqDto,
            HttpServletRequest req
    ) {
        User findUser = sessionService.findUserBySession(req);
        userService.deleteUser(findUser, reqDto.getPassword());
        sessionService.logout(req);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}