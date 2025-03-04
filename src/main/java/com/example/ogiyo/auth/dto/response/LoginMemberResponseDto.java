package com.example.ogiyo.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMemberResponseDto {

    private final Long id;

    private final String email;

    @Builder
    public LoginMemberResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
