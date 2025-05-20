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
    public UserResDto updateUserPassword(Long id, String oldPassword, String newPassword) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        // 비밀번호로 비교
        if (!findUser.getPassword().equals(oldPassword)) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
        findUser.updateUserPassword(newPassword);
        log.info("유저 수정 : name = {}", findUser.getUserName());
        return new UserResDto(findUser);
    }

    public void deleteUser(String password, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            UserResDto loginUser = (UserResDto) session.getAttribute("loginUser");
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