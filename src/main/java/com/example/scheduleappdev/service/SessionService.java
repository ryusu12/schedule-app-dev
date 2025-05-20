package com.example.scheduleappdev.service;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;

    public void makeSession(HttpServletRequest req, UserResDto userResDto) {
        HttpSession session = req.getSession();
        session.setAttribute(Const.LOGIN_USER, userResDto);
        log.info("로그인 성공 : name = {}", userResDto.getUserName());
    }

    public void logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("로그아웃");
        }
    }

    public User findUserBySession(HttpServletRequest req) {
        HttpSession session = validateSession(req);
        UserResDto loginUser = (UserResDto) session.getAttribute(Const.LOGIN_USER);

        return userRepository.findByIdOrElseThrow(loginUser.getUserId());
    }

    private HttpSession validateSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            log.warn("세션이 존재하지 않습니다.");
            throw new UnauthorizedException("세션이 존재하지 않습니다.");
        }
        return session;
    }

}