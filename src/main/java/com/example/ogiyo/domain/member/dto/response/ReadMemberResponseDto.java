package com.example.ogiyo.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReadMemberResponseDto {
    private final Long id;
    private final String name;
    private final String email;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    @Builder
    public ReadMemberResponseDto(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
