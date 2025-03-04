package com.example.ogiyo.auth.controller;

import com.example.ogiyo.auth.dto.request.LoginMemberRequestDto;
import com.example.ogiyo.auth.dto.request.SignUpMemberRequestDto;
import com.example.ogiyo.auth.dto.response.LoginMemberResponseDto;
import com.example.ogiyo.auth.dto.response.SignUpMemberResponseDto;
import com.example.ogiyo.auth.service.AuthService;
import com.example.ogiyo.global.etc.JwtProperties;
import com.example.ogiyo.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<SignUpMemberResponseDto> signUp(
            @Validated @RequestBody SignUpMemberRequestDto requestDto
    ) {
        SignUpMemberResponseDto responseDto = authService.signUp(
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginMemberResponseDto> login(
            @Validated @RequestBody LoginMemberRequestDto requestDto
    ) {
        LoginMemberResponseDto responseDto = authService.login(requestDto.getEmail(), requestDto.getPassword());
        String token = jwtUtil.generateToken(responseDto.getId(), requestDto.getEmail());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

}
