package com.example.ogiyo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginUserException extends ResponseStatusException {
    public LoginUserException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NOT_LOGIN.getMessage());
    }
}
