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

    public UserResDto createUser(String userName, String userEmail, String password) {
        User user = new User(userName, userEmail, password);
        return new UserResDto(userRepository.save(user));
    }

    public UserResDto login(String userEmail, String password) {
        User user = userRepository.findUserByUserEmailAndPasswordOrElseThrow(userEmail, password);
        return new UserResDto(user);
    }

    public List<UserResDto> findAllUsers() {
        return userRepository.findAll().stream().map(UserResDto::new).toList();
    }

    public UserResDto findUserById(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        return new UserResDto(user);
    }

    @Transactional
    public UserResDto updateUserPassword(Long id, String oldPassword, String newPassword) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        // 비밀번호로 비교
        if (!findUser.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        findUser.updateUserPassword(newPassword);
        return new UserResDto(findUser);
    }

    public void deleteUserById(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(findUser);
    }

}