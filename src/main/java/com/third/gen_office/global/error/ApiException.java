package com.third.gen_office.global.error;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final String messageKey;

    public ApiException(HttpStatus status, String code, String messageKey) {
        super(messageKey);
        this.status = status;
        this.code = code;
        this.messageKey = messageKey;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
