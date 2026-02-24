package com.third.gen_office.global.error;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {
    public ConflictException(String messageKey) {
        super(HttpStatus.CONFLICT, "CONFLICT", messageKey);
    }
}
