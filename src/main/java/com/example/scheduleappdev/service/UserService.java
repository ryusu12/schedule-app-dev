package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResDto createUser(String userName, String userEmail) {
        User user = new User(userName, userEmail);
        User newUser = userRepository.save(user);
        return new UserResDto(newUser);
    }

    public UserResDto findUserById(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        return new UserResDto(user);
    }

    public List<UserResDto> findAllUsers() {
        return userRepository.findAll().stream().map(UserResDto::new).toList();
    }

    @Transactional
    public UserResDto updateUser(Long id, String userName, String userEmail) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        // 이름으로 비교
        if (!findUser.getUserName().equals(userName)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유저가 일치하지 않습니다.");
        }
        findUser.updateUserEmail(userEmail);
        return new UserResDto(findUser);
    }
}