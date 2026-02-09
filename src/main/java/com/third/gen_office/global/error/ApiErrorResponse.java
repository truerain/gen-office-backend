package com.third.gen_office.global.error;

public record ApiErrorResponse(
    String code,
    String messageKey,
    String message,
    String locale,
    String path,
    String timestamp,
    String traceId
) {}
