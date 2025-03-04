package com.example.ogiyo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginUserException extends ResponseStatusException {
    public LoginUserException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NOT_LOGIN.getMessage());
    }
}
