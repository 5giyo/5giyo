package com.example.ogiyo.domain.member.service;

import com.example.ogiyo.auth.enums.MemberRole;
import com.example.ogiyo.common.config.PasswordEncoder;
import com.example.ogiyo.common.exception.InvalidPasswordException;
import com.example.ogiyo.common.exception.NotFoundUserException;
import com.example.ogiyo.domain.member.dto.request.DeleteMemberRequestDto;
import com.example.ogiyo.domain.member.dto.request.UpdateMemberRequestDto;
import com.example.ogiyo.domain.member.dto.request.UpdatePasswordRequestDto;
import com.example.ogiyo.domain.member.dto.response.ReadMemberResponseDto;
import com.example.ogiyo.domain.member.dto.response.UpdateMemberResponseDto;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    void Member를_ID로_조회할_수_있다(){
        //given
        Long memberId = 1L;
        String email = "test@gmail.com";
        Member member = new Member("abc", email, "password", MemberRole.GENERAL);
        ReflectionTestUtils.setField(member, "id", memberId);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        //when
        ReadMemberResponseDto dto = memberService.getMemberById(memberId);

        //then
        assertNotNull(dto);
        assertEquals(memberId, dto.getId());
        assertEquals(email, dto.getEmail());
    }

    @Test
    void 존재하지_않는_Member_조회시_Exception을_던진다(){
        //given
        Long memberId = 1L;
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());
        //when & then
        assertThrows(NotFoundUserException.class,
                ()-> memberService.getMemberById(memberId),
                "없는 사용자 입니다."
        );
    }

    @Test
    void Member를_삭제할_수_있다(){
        //given
        Long memberId = 1L;
        String password = "password";
        Member member = new Member("abc", "abc@abc.com", password, MemberRole.GENERAL);
        ReflectionTestUtils.setField(member, "id", memberId);

        DeleteMemberRequestDto dto = new DeleteMemberRequestDto(password);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(true);

        //when
        memberService.deleteMember(dto, memberId);

        //then
        assertTrue(member.isDeleted());
    }

    @Test
    void Member를_Update할_수_있다(){
        //given
        Long memberId = 1L;
        String oldName = "abc";
        String newName = "updatedName";
        String oldEmail = "abc@abc.com";
        String newEmail = "updated@abc.com";
        String password = "password";

        Member member = new Member(oldName, oldEmail, password, MemberRole.GENERAL);
        ReflectionTestUtils.setField(member, "id", memberId);

        UpdateMemberRequestDto dto = new UpdateMemberRequestDto(newName, newEmail, password);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(true);

        //when
        UpdateMemberResponseDto response = memberService.updateMember(dto, memberId);

        //then
        assertEquals(memberId, response.getId());
        assertEquals(newName, response.getName());
        assertEquals(newEmail, response.getEmail());
        assertEquals(newName, member.getName());
        assertEquals(newEmail, member.getEmail());
    }

    @Test
    void Member_Password를_Update할_수_있다(){
        // given
        Long memberId = 1L;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";

        Member member = new Member("abc", "abc@abc.com", oldPassword, MemberRole.GENERAL);
        ReflectionTestUtils.setField(member, "id", memberId);

        UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto(oldPassword, newPassword, newPassword);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(passwordEncoder.encode(anyString())).willReturn(encodedNewPassword);

        // when
        memberService.updateMemberPassword(dto, memberId);

        // then
        assertEquals(encodedNewPassword, member.getPassword());
    }

    @Test
    void 비밀번호가_틀리면_Member_Password_변경에_실패한다() {
        // given
        Long memberId = 1L;
        String oldPassword = "oldPassword";
        String wrongPassword = "wrongPassword";
        String newPassword = "newPassword";

        Member member = new Member("abc", "abc@abc.com", oldPassword, MemberRole.GENERAL);
        ReflectionTestUtils.setField(member, "id", memberId);

        UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto(wrongPassword, newPassword, newPassword);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(eq(wrongPassword), anyString())).willReturn(false);

        // when & then
        assertThrows(InvalidPasswordException.class, () -> memberService.updateMemberPassword(dto, memberId));
    }

    @Test
    void 비밀번호가_틀리면_Member_정보_수정에_실패한다() {
        // given
        Long memberId = 1L;
        String oldName = "abc";
        String newName = "updatedName";
        String oldEmail = "abc@abc.com";
        String newEmail = "updated@abc.com";
        String password = "password";
        String wrongPassword = "wrongPassword";

        Member member = new Member(oldName, oldEmail, password, MemberRole.GENERAL);
        ReflectionTestUtils.setField(member, "id", memberId);

        UpdateMemberRequestDto dto = new UpdateMemberRequestDto(newName, newEmail, wrongPassword);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(eq(wrongPassword), anyString())).willReturn(false);

        // when & then
        assertThrows(InvalidPasswordException.class, () -> memberService.updateMember(dto, memberId));
    }

    @Test
    void 비밀번호가_틀리면_Member_삭제에_실패한다() {
        // given
        Long memberId = 1L;
        String password = "password";
        String wrongPassword = "wrongPassword";

        Member member = new Member("abc", "abc@abc.com", password, MemberRole.GENERAL);
        ReflectionTestUtils.setField(member, "id", memberId);

        DeleteMemberRequestDto dto = new DeleteMemberRequestDto(wrongPassword);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(eq(wrongPassword), anyString())).willReturn(false);

        // when & then
        assertThrows(InvalidPasswordException.class, () -> memberService.deleteMember(dto, memberId));
    }

    @Test
    void 존재하지_않는_Member의_비밀번호를_변경하려_하면_Exception을_던진다() {
        // given
        Long memberId = 1L;
        given(memberRepository.findById(anyLong())).willReturn(Optional.empty());

        UpdatePasswordRequestDto dto = new UpdatePasswordRequestDto("oldPassword", "newPassword", "newPassword");

        // when & then
        assertThrows(NotFoundUserException.class, () -> memberService.updateMemberPassword(dto, memberId));
    }
}