package com.example.ogiyo.auth.dto.request;

import com.example.ogiyo.common.etc.Const;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpMemberRequestDto {

    @NotNull
    @Email
    @Pattern(regexp = Const.EMAIL_REGEX)
    @Size(min = 8, max = 100)
    private final String email;

    @NotNull
    @Pattern(regexp = Const.PASSWORD_REGEX)
    @Size(min = 8, max = 20)
    private final String password;
}
