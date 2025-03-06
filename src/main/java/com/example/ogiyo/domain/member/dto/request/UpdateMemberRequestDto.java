package com.example.ogiyo.domain.member.dto.request;

import com.example.ogiyo.common.etc.Const;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberRequestDto {
    private final String name;

    @Pattern(regexp = Const.EMAIL_REGEX)
    @Email
    @Size(min = 10, max = 30)
    private final String email;

    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = Const.PASSWORD_REGEX)
    private final String password;
}
