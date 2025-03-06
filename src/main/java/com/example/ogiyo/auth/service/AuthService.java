package com.example.ogiyo.auth.service;

import com.example.ogiyo.auth.dto.response.LoginMemberResponseDto;
import com.example.ogiyo.auth.dto.response.SignUpMemberResponseDto;
import com.example.ogiyo.auth.enums.MemberRole;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.common.exception.DuplicateEmailException;
import com.example.ogiyo.common.exception.InvalidPasswordOrEmailException;
import com.example.ogiyo.common.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.ogiyo.common.config.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpMemberResponseDto signUp(String email, String password, MemberRole role) {
        if(memberService.existsByEmail(email)){
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .role(role)
                .build();

        Member savedMember = memberService.saveMember(member);

        return SignUpMemberResponseDto.builder()
                .id(savedMember.getId())
                .email(savedMember.getEmail())
                .createdAt(savedMember.getCreatedAt())
                .role(role)
                .build();
    }

    @Transactional(readOnly = true)
    public LoginMemberResponseDto login(String email, String password) {
        Member member = memberService.findByEmail(email).orElseThrow(NotFoundUserException::new);

        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new InvalidPasswordOrEmailException();
        }

        return LoginMemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
