package com.example.ogiyo.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public class ErrorMessage {
    private final String message;
    private final Integer httpStatusCode;

    public ErrorMessage(ErrorCode errorCode, HttpStatusCode httpStatusCode) {
        this.message = errorCode.getMessage();
        this.httpStatusCode = httpStatusCode.value();
    }
}
