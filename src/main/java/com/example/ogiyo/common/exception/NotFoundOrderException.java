package com.example.ogiyo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundOrderException extends ResponseStatusException {
    public NotFoundOrderException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.NOT_FOUND_ORDER.getMessage());
    }
}
