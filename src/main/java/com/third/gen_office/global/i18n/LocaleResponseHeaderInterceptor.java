package com.third.gen_office.global.i18n;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class LocaleResponseHeaderInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        org.springframework.web.servlet.ModelAndView modelAndView
    ) {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale != null) {
            response.setHeader("Content-Language", locale.toLanguageTag());
        }
    }
}
