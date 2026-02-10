package com.third.gen_office.global.i18n;

import java.util.List;

import com.third.gen_office.infrastructure.authentication.RequestLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/i18n")
public class I18nController {
    private static final Logger log = LoggerFactory.getLogger(I18nController.class);
    private final JdbcTemplate jdbcTemplate;

    public I18nController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public I18nItemsResponse list(
        @RequestParam("locale") String locale,
        @RequestParam("ns") String namespace
    ) {
        String langCd = normalizeLocale(locale);
        List<I18nItemResponse> items = jdbcTemplate.query(
            "SELECT message_cd, message_txt FROM tb_cm_message WHERE lang_cd = ? AND namespace = ?",
            (rs, rowNum) -> new I18nItemResponse(
                rs.getString("message_cd"),
                rs.getString("message_txt")
            ),
            langCd,
            namespace
        );
        return new I18nItemsResponse(items);
    }

    private String normalizeLocale(String locale) {
        if (locale == null || locale.isBlank()) {
            return "ko";
        }
        return locale;
    }
}
