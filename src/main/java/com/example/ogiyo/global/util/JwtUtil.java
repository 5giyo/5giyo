package com.example.ogiyo.global.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.ogiyo.auth.service.AuthService;
import com.example.ogiyo.global.etc.JwtProperties;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final AuthService authService;

    // 토큰 생성
    public String generateToken(Long id, String email) {
        return JWT.create()
                .withSubject(JwtProperties.APP_TITLE)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", id)
                .withClaim("email", email)
                .sign(Algorithm.HMAC256(key.getEncoded()));
    }

    // 토큰에서 이메일 값 가져오기
    public String extractEmail(String token) {
        String reToken = token.replace(JwtProperties.TOKEN_PREFIX, "");
        return JWT.require(Algorithm.HMAC256(key.getEncoded())).build().verify(reToken).getClaim("email").asString();
    }

    // 토큰에서 유저 Id 값 가져오기
    public Long extractUserId(String token) {
        String reToken = token.replace(JwtProperties.TOKEN_PREFIX, "");
        return Long.parseLong(JWT.require(Algorithm.HMAC256(key.getEncoded())).build().verify(reToken).getClaim("id").toString());
    }
}
