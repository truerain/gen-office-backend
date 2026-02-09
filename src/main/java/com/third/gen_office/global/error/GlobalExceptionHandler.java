package com.third.gen_office.global.error;

import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.Locale;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String DEFAULT_MESSAGE_KEY = "common.internal_error";

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
        ApiException ex,
        HttpServletRequest request
    ) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(ex.getMessageKey(), null, ex.getMessageKey(), locale);
        ApiErrorResponse response = buildResponse(
            ex.getCode(),
            ex.getMessageKey(),
            message,
            locale,
            request
        );
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
        Exception ex,
        HttpServletRequest request
    ) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(DEFAULT_MESSAGE_KEY, null, DEFAULT_MESSAGE_KEY, locale);
        ApiErrorResponse response = buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.name(),
            DEFAULT_MESSAGE_KEY,
            message,
            locale,
            request
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ApiErrorResponse buildResponse(
        String code,
        String messageKey,
        String message,
        Locale locale,
        HttpServletRequest request
    ) {
        String traceId = MDC.get("traceId");
        return new ApiErrorResponse(
            code,
            messageKey,
            message,
            locale == null ? null : locale.toLanguageTag(),
            request.getRequestURI(),
            OffsetDateTime.now().toString(),
            traceId
        );
    }
}
