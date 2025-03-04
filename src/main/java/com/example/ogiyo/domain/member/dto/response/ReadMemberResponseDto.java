package com.example.ogiyo.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReadMemberResponseDto {
    private final Long id;

    private final String email;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    @Builder
    public ReadMemberResponseDto(Long id, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
