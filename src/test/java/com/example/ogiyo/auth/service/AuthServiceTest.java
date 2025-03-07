package com.example.ogiyo.auth;

import com.example.ogiyo.auth.dto.response.LoginMemberResponseDto;
import com.example.ogiyo.auth.dto.response.SignUpMemberResponseDto;
import com.example.ogiyo.auth.enums.MemberRole;
import com.example.ogiyo.auth.service.AuthService;
import com.example.ogiyo.common.config.PasswordEncoder;
import com.example.ogiyo.common.exception.DuplicateEmailException;
import com.example.ogiyo.common.exception.InvalidPasswordOrEmailException;
import com.example.ogiyo.common.exception.NotFoundUserException;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberService memberService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void 회원가입() {
        //given
        Long memberId = 1L;
        String name = "test";
        String email = "test@gmail.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        MemberRole role = MemberRole.GENERAL;

        Member member = new Member(name, email, password, role);
        ReflectionTestUtils.setField(member, "id", memberId);

        given(memberService.existsByEmail(email)).willReturn(false);
        given(passwordEncoder.encode(password)).willReturn(encodedPassword);
        doReturn(member).when(memberService).saveMember(any(Member.class));

        //when
        SignUpMemberResponseDto response = authService.signUp(email, password, role);

        //then
        assertNotNull(response);
        assertEquals(memberId, response.getId());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void 중복된_이메일로_회원가입시_Exception을_던진다() {
        // given
        String email = "duplicate@gmail.com";
        String password = "password";
        MemberRole role = MemberRole.GENERAL;

        given(memberService.existsByEmail(email)).willReturn(true);

        // when & then
        assertThrows(DuplicateEmailException.class, () -> authService.signUp(email, password, role));
    }

    @Test
    void 로그인_성공() {
        // given
        Long memberId = 1L;
        String name = "test";
        String email = "test@gmail.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        MemberRole role = MemberRole.GENERAL;

        Member member = new Member(name, email, password, role);
        ReflectionTestUtils.setField(member, "id", memberId);

        given(memberService.findByEmail(email)).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        LoginMemberResponseDto response = authService.login(email, password);

        // then
        assertNotNull(response);
        assertEquals(memberId, response.getId());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void 존재하지_않는_이메일로_로그인시_Exception을_던진다() {
        // given
        String email = "notfound@gmail.com";
        String password = "password";

        given(memberService.findByEmail(anyString())).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundUserException.class, () -> authService.login(email, password));
    }

    @Test
    void 비밀번호가_틀리면_로그인_실패() {
        // given
        Long memberId = 1L;
        String name = "test";
        String email = "test@gmail.com";
        String password = "wrongPassword";
        String encodedPassword = "encodedPassword";
        MemberRole role = MemberRole.GENERAL;

        Member member = new Member(name, email, password, role);
        ReflectionTestUtils.setField(member, "id", memberId);

        given(memberService.findByEmail(email)).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(false);

        // when & then
        assertThrows(InvalidPasswordOrEmailException.class, () -> authService.login(email, password));
    }
}