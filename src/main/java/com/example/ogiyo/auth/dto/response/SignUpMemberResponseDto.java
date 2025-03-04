package com.example.ogiyo.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpMemberResponseDto {
    private final long id;
    private final String email;
    private final LocalDateTime createdAt;

    @Builder
    public SignUpMemberResponseDto(long id, String email, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
    }
}
