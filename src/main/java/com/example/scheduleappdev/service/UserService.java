package com.example.scheduleappdev.service;

import com.example.scheduleappdev.config.PasswordEncoder;
import com.example.scheduleappdev.common.Const;
import com.example.scheduleappdev.dto.UserResDto;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.UnauthorizedException;
import com.example.scheduleappdev.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
        User user = userRepository.findUserByUserEmailOrElseThrow(userEmail);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
        return new UserResDto(user);
    }

    public void makeSession(HttpServletRequest req, UserResDto userResDto) {
        HttpSession session = req.getSession();
        session.setAttribute(Const.LOGIN_USER, userResDto);
        log.info("로그인 성공 : name = {}", userResDto.getUserName());
    }

    public void logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃");
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
    public UserResDto updateUserPassword(Long id, String oldPassword, String newPassword) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        // 비밀번호로 비교
        if (!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        findUser.updateUserPassword(encodedPassword);

        log.info("유저 수정 : name = {}", findUser.getUserName());
        return new UserResDto(findUser);
    }

    public void deleteUser(String password, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserResDto loginUser = (UserResDto) session.getAttribute(Const.LOGIN_USER);
            User findUser = userRepository.findByIdOrElseThrow(loginUser.getUserId());

            if (!findUser.getPassword().equals(password)) {
                throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
            }
            log.info("회원탈퇴 : name = {}", findUser.getUserName());
            userRepository.delete(findUser);

            log.info("로그아웃");
            session.invalidate();
        }
    }

}