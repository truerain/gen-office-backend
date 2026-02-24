package com.third.gen_office.global.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    public BadRequestException(String messageKey) {
        super(HttpStatus.BAD_REQUEST, "BAD_REQUEST", messageKey);
    }
}
