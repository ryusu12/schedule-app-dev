package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.CreateUserReqDto;
import com.example.scheduleappdev.dto.LoginUserReqDto;
import com.example.scheduleappdev.dto.UpdateUserPasswordReqDto;
import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/signup")
    public ResponseEntity<UserResDto> createUser(@RequestBody CreateUserReqDto reqDto) {
        UserResDto userResDto = userService.createUser(reqDto.getUserName(), reqDto.getUserEmail(), reqDto.getPassword());
        return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginUserReqDto reqDto,
            HttpServletRequest req
    ) {
        UserResDto userResDto = userService.login(reqDto.getUserEmail(), reqDto.getPassword());

        HttpSession session = req.getSession();
        session.setAttribute("loginUser", userResDto);

        log.info("로그인 성공 : name = {}", userResDto.getUserName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃");
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

    @PatchMapping("/{id}")
    public ResponseEntity<UserResDto> updateUserPassword(@PathVariable Long id, @RequestBody UpdateUserPasswordReqDto reqDto) {
        UserResDto userResDto = userService.updateUserPassword(id, reqDto.getOldPassword(), reqDto.getNewPassword());
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}