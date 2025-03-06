package com.example.ogiyo.auth.dto.response;

import com.example.ogiyo.auth.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMemberResponseDto {

    private final Long id;
    private final String email;
    private final MemberRole role;

    @Builder
    public LoginMemberResponseDto(Long id, String email, MemberRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
