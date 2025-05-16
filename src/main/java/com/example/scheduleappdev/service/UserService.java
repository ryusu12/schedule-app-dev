package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}