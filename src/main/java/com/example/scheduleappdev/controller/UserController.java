package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.CreateUserReqDto;
import com.example.scheduleappdev.dto.LoginUserReqDto;
import com.example.scheduleappdev.dto.UpdateUserPasswordReqDto;
import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> login(
            @RequestBody LoginUserReqDto reqDto,
            HttpServletRequest req
    ) {
        UserResDto userResDto = userService.login(reqDto.getUserEmail(), reqDto.getPassword());

        HttpSession session = req.getSession();
        session.setAttribute("loginUser", userResDto);

        return new ResponseEntity<>("로그인 성공 : " + userResDto.getUserName(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>("로그아웃", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResDto>> findAllUsers() {
        List<UserResDto> userResDtoList = userService.findAllUsers();
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