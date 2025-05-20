package com.example.scheduleappdev.filter;

import com.example.scheduleappdev.common.Const;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/user/signup", "/user/login", "/user/logout"};

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse res,
            FilterChain chain
    )throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        String reqURI = httpReq.getRequestURI();

        if(!isWhiteList(reqURI)) {
            HttpSession session = httpReq.getSession(false);
            if(session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("timestamp", LocalDateTime.now().toString());
                errorBody.put("status", HttpStatus.UNAUTHORIZED.value());
                errorBody.put("error", "UNAUTHORIZED");
                errorBody.put("message", "로그인이 필요합니다.");
                errorBody.put("path", reqURI);

                String json = new ObjectMapper().writeValueAsString(errorBody);

                httpRes.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpRes.setContentType("application/json");
                httpRes.setCharacterEncoding("UTF-8");
                httpRes.getWriter().write(json);

                log.warn("로그인이 필요합니다.");
                return;
            }
        }
        chain.doFilter(req, res);
    }

    public boolean isWhiteList(String reqURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, reqURI);
    }

}