package com.example.ogiyo.common.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "code", "errorMessage", "data"})
public class ResponseDto<T> {
    private final Boolean success;
    private final int status;  // HTTP 상태 코드 추가
    private final String code;
    private final String errorMessage;
    private final T data;


    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, HttpStatus.OK.value(), HttpStatus.OK.name(), null, data);
    }

    public static <T> ResponseDto<T> fail(HttpStatus status, String code, String errorMessage) {
        return new ResponseDto<>(false, status.value(), code, errorMessage, null);
    }
}