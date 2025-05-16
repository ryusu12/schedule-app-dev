package com.example.scheduleappdev.controller;

import com.example.scheduleappdev.dto.CreateUserReqDto;
import com.example.scheduleappdev.dto.UpdateUserEmailReqDto;
import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.service.UserService;
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

    @PostMapping()
    public ResponseEntity<UserResDto> createUser(@RequestBody CreateUserReqDto reqDto) {
        UserResDto userResDto = userService.createUser(reqDto.getUserName(), reqDto.getUserEmail());
        return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
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
    public ResponseEntity<UserResDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserEmailReqDto reqDto) {
        UserResDto userResDto = userService.updateUser(id, reqDto.getUserName(), reqDto.getUserEmail());
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }
}