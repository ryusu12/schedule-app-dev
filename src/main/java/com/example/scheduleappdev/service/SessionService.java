package com.example.scheduleappdev.service;

import com.example.scheduleappdev.common.Const;
import com.example.scheduleappdev.dto.res.UserResDto;
import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;

    // 로그인
    public void makeSession(HttpServletRequest req, UserResDto userResDto) {
        HttpSession session = req.getSession();
        session.setAttribute(Const.LOGIN_USER, userResDto);
        log.info("로그인 성공 : name = {}", userResDto.getUserName());
    }

    // 로그아웃
    public void logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("로그아웃");
        }
    }

    // 유저 정보 가져오기
    public User findUserBySession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        UserResDto loginUser = (UserResDto) session.getAttribute(Const.LOGIN_USER);
        return userRepository.findUserByIdOrElseThrow(loginUser.getUserId());
    }

}