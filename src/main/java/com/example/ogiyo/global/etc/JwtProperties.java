package com.example.ogiyo.global.etc;

public interface JwtProperties {
    String HEADER_STRING = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    int EXPIRATION_TIME = 1000 * 60 * 60;
    String APP_TITLE = "ogiyo";
}