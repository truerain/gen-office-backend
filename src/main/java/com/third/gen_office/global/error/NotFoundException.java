package com.third.gen_office.global.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(String messageKey) {
        super(HttpStatus.NOT_FOUND, "NOT_FOUND", messageKey);
    }
}
