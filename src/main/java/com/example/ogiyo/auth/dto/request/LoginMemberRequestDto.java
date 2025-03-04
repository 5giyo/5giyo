package com.example.ogiyo.auth.dto.request;

import com.example.ogiyo.global.etc.Const;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginMemberRequestDto {
    @NotNull
    @Pattern(regexp = Const.EMAIL_REGEX)
    @Size(min = 8, max = 30)
    private final String email;

    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = Const.PASSWORD_REGEX)
    private final String password;

}
