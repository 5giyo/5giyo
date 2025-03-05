package com.example.ogiyo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundUserException extends ResponseStatusException {
    public NotFoundUserException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER.getMessage());
    }
}
