package com.third.gen_office.infrastructure.authentication;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class CsrfDebugLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(CsrfDebugLoggingFilter.class);

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String headerToken = request.getHeader("X-XSRF-TOKEN");
        String cookieToken = findCookieValue(request.getCookies(), "XSRF-TOKEN");
        log.info("csrf_check method={} path={} headerToken={} cookieToken={}",
            request.getMethod(),
            request.getRequestURI(),
            headerToken,
            cookieToken
        );
        filterChain.doFilter(request, response);
    }

    private String findCookieValue(Cookie[] cookies, String name) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
