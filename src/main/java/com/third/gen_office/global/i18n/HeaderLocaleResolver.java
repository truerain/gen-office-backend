package com.third.gen_office.global.i18n;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

public class HeaderLocaleResolver implements LocaleResolver {
    private static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("ko");

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String header = request.getHeader("X-Lang");
        if (StringUtils.hasText(header)) {
            return Locale.forLanguageTag(cleanTag(header));
        }

        String acceptLanguage = request.getHeader("Accept-Language");
        if (StringUtils.hasText(acceptLanguage)) {
            return Locale.forLanguageTag(extractPrimaryTag(acceptLanguage));
        }

        Object userLang = request.getAttribute("userLang");
        if (userLang instanceof String userLangCd && StringUtils.hasText(userLangCd)) {
            return Locale.forLanguageTag(cleanTag(userLangCd));
        }

        return DEFAULT_LOCALE;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // Locale is derived from headers and request attributes only.
    }

    private String extractPrimaryTag(String acceptLanguage) {
        String[] parts = acceptLanguage.split(",");
        if (parts.length == 0) {
            return DEFAULT_LOCALE.toLanguageTag();
        }
        return cleanTag(parts[0]);
    }

    private String cleanTag(String rawTag) {
        int semicolon = rawTag.indexOf(';');
        String tag = semicolon >= 0 ? rawTag.substring(0, semicolon) : rawTag;
        return tag.trim();
    }
}
