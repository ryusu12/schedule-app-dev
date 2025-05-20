package com.example.scheduleappdev.service;

import com.example.scheduleappdev.config.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserResDto createUser(String userName, String userEmail, String password) {
        userRepository.isExistUserNameOrEmail(userName, userEmail);
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, userEmail, encodedPassword);
        log.info("유저 생성 : name = {}", userName);
        return new UserResDto(userRepository.save(user));
    }

    public UserResDto login(String userEmail, String password) {
        User findUser = userRepository.findUserByUserEmailOrElseThrow(userEmail);
        checkUserPassword(password, findUser);
        return new UserResDto(findUser);
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
        checkUserPassword(oldPassword, user);

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updateUserPassword(encodedPassword);

        log.info("유저 수정 : name = {}", user.getUserName());
        return new UserResDto(user);
    }

    public void deleteUser(User user, String password) {
        checkUserPassword(password, user);
        log.info("회원탈퇴 : name = {}", user.getUserName());
        userRepository.delete(user);
    }

    public void checkUserPassword(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
    }

}