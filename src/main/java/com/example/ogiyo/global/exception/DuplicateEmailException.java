package com.example.ogiyo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateEmailException extends ResponseStatusException {
    public DuplicateEmailException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATION_EMAIL.getMessage());
    }
}
