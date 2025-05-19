package com.example.scheduleappdev.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

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
            if(session == null || session.getAttribute("loginUser") == null) {
                httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpRes.setContentType("text/plain; charset=utf-8");
                httpRes.getWriter().write("로그인이 필요합니다.");
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