package com.example.ogiyo.auth.dto.response;

import com.example.ogiyo.auth.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpMemberResponseDto {
    private final long id;
    private final String email;
    private final MemberRole role;
    private final LocalDateTime createdAt;

    @Builder
    public SignUpMemberResponseDto(long id, String email, MemberRole role, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }
}
