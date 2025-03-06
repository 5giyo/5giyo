package com.example.ogiyo.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateMemberResponseDto {
    private final Long id;

    private final String name;

    private final String email;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    @Builder
    public UpdateMemberResponseDto(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
