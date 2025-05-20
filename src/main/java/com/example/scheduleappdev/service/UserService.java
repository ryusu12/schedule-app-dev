package com.example.scheduleappdev.service;

import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.UnauthorizedException;
import com.example.scheduleappdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResDto createUser(String userName, String userEmail, String password) {
        userRepository.isExistUserNameOrEmail(userName, userEmail);
        User user = new User(userName, userEmail, password);
        log.info("유저 생성 : name = {}", userName);
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
        log.info("유저 조회 : name = {}", user.getUserName());
        return new UserResDto(user);
    }

    @Transactional
    public UserResDto updateUserPassword(User user, String oldPassword, String newPassword) {
        checkUserPassword(user, oldPassword);
        user.updateUserPassword(newPassword);
        log.info("유저 수정 : name = {}", user.getUserName());
        return new UserResDto(user);
    }

    public void deleteUser(User user, String password) {
        checkUserPassword(user, password);
        log.info("회원탈퇴 : name = {}", user.getUserName());
        userRepository.delete(user);
    }

    private void checkUserPassword(User user, String password) {
        if (!user.getPassword().equals(password)) {
            log.warn("비밀번호가 일치하지 않습니다.");
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }

}