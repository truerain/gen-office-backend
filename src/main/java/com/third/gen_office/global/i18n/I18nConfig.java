package com.third.gen_office.global.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class I18nConfig implements WebMvcConfigurer {
    @Bean
    public DbMessageSource messageSource(JdbcTemplate jdbcTemplate) {
        return new DbMessageSource(jdbcTemplate);
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new HeaderLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleResponseHeaderInterceptor());
    }
}
