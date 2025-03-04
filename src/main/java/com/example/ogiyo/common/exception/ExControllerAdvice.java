package com.example.ogiyo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorMessage> duplicateEmailExHandle(DuplicateEmailException e){
        return new ResponseEntity<>(new ErrorMessage(ErrorCode.DUPLICATION_EMAIL, e.getStatusCode()), e.getStatusCode());
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorMessage> notFoundUserExHandle(NotFoundUserException e) {
        return new ResponseEntity<>(new ErrorMessage(ErrorCode.NOT_FOUND_USER, e.getStatusCode()), e.getStatusCode());
    }

    @ExceptionHandler(InvalidPasswordOrEmailException.class)
    public ResponseEntity<ErrorMessage> invalidPasswordOrEmailExHandle(InvalidPasswordOrEmailException e) {
        return new ResponseEntity<>(new ErrorMessage(ErrorCode.INVALID_EMAIL_PASSWORD, e.getStatusCode()), e.getStatusCode());
    }

    @ExceptionHandler(LoginUserException.class)
    public ResponseEntity<ErrorMessage> loginUserExHandle(LoginUserException e) {
        return new ResponseEntity<>(new ErrorMessage(ErrorCode.NOT_LOGIN, e.getStatusCode()), e.getStatusCode());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> invalidPasswordExHandleExHandle(InvalidPasswordException e) {
        return new ResponseEntity<>(new ErrorMessage(ErrorCode.INVALID_PASSWORD, e.getStatusCode()), e.getStatusCode());
    }

    @ExceptionHandler(MustChangePasswordException.class)
    public ResponseEntity<ErrorMessage> sameAsCurrentPasswordException(MustChangePasswordException e) {
        return new ResponseEntity<>(new ErrorMessage(ErrorCode.CANNOT_USE_SAME_PASSWORD, e.getStatusCode()), e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> globalValidatedExHandle(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                (error) -> {
                    String fieldName = ( (FieldError) error ).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
