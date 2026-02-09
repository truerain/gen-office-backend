package com.third.gen_office.global.i18n;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Locale;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbMessageSource extends AbstractMessageSource {
    private static final String MISSING = "__MISSING__";
    private static final String DEFAULT_LANGUAGE = "ko";

    private final JdbcTemplate jdbcTemplate;
    private final Cache<String, String> cache;

    public DbMessageSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10))
            .maximumSize(10_000)
            .build();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        MessageKey messageKey = MessageKey.parse(code);
        String langCd = toLangCd(locale);
        String message = resolveMessage(messageKey, langCd);
        return new MessageFormat(message, locale);
    }

    private String resolveMessage(MessageKey key, String langCd) {
        String cacheKey = key.namespace() + "|" + key.messageCd() + "|" + langCd;
        String cached = cache.getIfPresent(cacheKey);
        if (cached != null) {
            return cached.equals(MISSING) ? key.rawCode() : cached;
        }

        String message = queryMessage(key, langCd);
        if (message == null && langCd.contains("-")) {
            message = queryMessage(key, langCd.substring(0, langCd.indexOf('-')));
        }
        if (message == null && !DEFAULT_LANGUAGE.equals(langCd)) {
            message = queryMessage(key, DEFAULT_LANGUAGE);
        }
        cache.put(cacheKey, message == null ? MISSING : message);
        return message == null ? key.rawCode() : message;
    }

    private String queryMessage(MessageKey key, String langCd) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT message_txt FROM tb_cm_message WHERE namespace = ? AND message_cd = ? AND lang_cd = ?",
                String.class,
                key.namespace(),
                key.messageCd(),
                langCd
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private String toLangCd(Locale locale) {
        if (locale == null) {
            return DEFAULT_LANGUAGE;
        }
        String tag = locale.toLanguageTag();
        if (tag == null || tag.isBlank()) {
            return DEFAULT_LANGUAGE;
        }
        return tag;
    }
}
