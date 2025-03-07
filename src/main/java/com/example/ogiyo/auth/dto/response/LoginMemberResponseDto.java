package com.example.ogiyo.auth.dto.response;

import com.example.ogiyo.auth.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMemberResponseDto {

    private final Long id;
    private final String name;
    private final String email;
    private final MemberRole role;

    @Builder
    public LoginMemberResponseDto(Long id, String name, String email, MemberRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
