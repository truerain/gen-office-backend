package com.third.gen_office.infrastructure.authentication;

import java.io.IOException;
import java.time.Duration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AbsoluteSessionTimeoutFilter extends OncePerRequestFilter {
    private final long maxLifetimeMillis;

    public AbsoluteSessionTimeoutFilter(Duration maxLifetime) {
        this.maxLifetimeMillis = maxLifetime.toMillis();
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Object loginTime = session.getAttribute("loginTime");
                if (loginTime instanceof Long) {
                    long elapsed = System.currentTimeMillis() - (Long) loginTime;
                    if (elapsed > maxLifetimeMillis) {
                        session.invalidate();
                        SecurityContextHolder.clearContext();
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"message\":\"session_expired\"}");
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
