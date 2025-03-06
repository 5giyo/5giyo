package com.example.ogiyo.auth.controller;

import com.example.ogiyo.auth.dto.request.LoginMemberRequestDto;
import com.example.ogiyo.auth.dto.request.SignUpMemberRequestDto;
import com.example.ogiyo.auth.dto.response.LoginMemberResponseDto;
import com.example.ogiyo.auth.dto.response.SignUpMemberResponseDto;
import com.example.ogiyo.auth.service.AuthService;
import com.example.ogiyo.common.dto.JwtToken;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
                requestDto.getPassword(),
                requestDto.getRole()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginMemberResponseDto> login(
            @Validated @RequestBody LoginMemberRequestDto requestDto
    ) {
        LoginMemberResponseDto responseDto = authService.login(requestDto.getEmail(), requestDto.getPassword());
        JwtToken jwtToken = jwtUtil.generateToken(responseDto.getId(), requestDto.getEmail());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken.getAccessToken());
        httpHeaders.set(JwtProperties.REFRESH_HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken.getRefreshToken());

        return new ResponseEntity<>(responseDto, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(
            @CookieValue(value = JwtProperties.REFRESH_HEADER_STRING, required = false) String refreshToken
    ){
        if(refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = refreshToken.replace(JwtProperties.TOKEN_PREFIX, "");
        Long id = jwtUtil.extractMemberId(token);
        String email = jwtUtil.extractEmail(token);

        JwtToken jwtToken = jwtUtil.generateToken(id, email);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken.getAccessToken());
        httpHeaders.set(JwtProperties.REFRESH_HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken.getRefreshToken());

        return new ResponseEntity<>("Refresh Token", httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(value = JwtProperties.HEADER_STRING) String token
    ){
        Long id = jwtUtil.extractMemberId(token);
        String email = jwtUtil.extractEmail(token);
        JwtToken jwtToken = jwtUtil.generateExpiredToken(id, email);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken.getAccessToken());
        httpHeaders.set(JwtProperties.REFRESH_HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken.getRefreshToken());

        return new ResponseEntity<>("로그아웃 성공", httpHeaders, HttpStatus.OK);
    }
}
