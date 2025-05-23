package com.example.scheduleappdev.filter;

import com.example.scheduleappdev.common.Const;
import com.example.scheduleappdev.dto.res.err.ErrorResDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/user/signup", "/user/login", "/user/logout"};

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        String reqURI = httpReq.getRequestURI();

        if (!isWhiteList(reqURI)) {
            HttpSession session = httpReq.getSession(false);
            if (isNotLogin(session)) {
                sendUnauthorizedResponse(httpRes);
                log.warn("로그인이 필요합니다 : URI = {}", reqURI);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean isWhiteList(String reqURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, reqURI);
    }

    private boolean isNotLogin(HttpSession session) {
        return session == null || session.getAttribute(Const.LOGIN_USER) == null;
    }

    private void sendUnauthorizedResponse(HttpServletResponse res) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String json = new ObjectMapper().writeValueAsString(createErrorResDto(status));

        res.setStatus(status.value());
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(json);
    }

    private ErrorResDto createErrorResDto(HttpStatus status) {
        return new ErrorResDto(
                status.value(),
                status.getReasonPhrase(),
                "로그인이 필요합니다.",
                LocalDateTime.now()
        );
    }

}