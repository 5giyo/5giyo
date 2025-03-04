package com.example.ogiyo.global.etc;

public interface Const {
    String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; // 이메일 인증용
    String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$"; // 8글자 이상이야 되고, 대소문자 포함, 특수 문자 포함
}
