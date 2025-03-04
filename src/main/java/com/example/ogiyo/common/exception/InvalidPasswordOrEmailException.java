package com.example.ogiyo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordOrEmailException extends ResponseStatusException {
    public InvalidPasswordOrEmailException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_EMAIL_PASSWORD.getMessage());
    }
}
