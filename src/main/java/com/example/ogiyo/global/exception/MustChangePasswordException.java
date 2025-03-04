package com.example.ogiyo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MustChangePasswordException extends ResponseStatusException {
    public MustChangePasswordException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CANNOT_USE_SAME_PASSWORD.getMessage());
    }
}
