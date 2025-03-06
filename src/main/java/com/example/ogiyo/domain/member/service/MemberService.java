package com.example.ogiyo.domain.member.service;

import com.example.ogiyo.common.exception.InvalidPasswordException;
import com.example.ogiyo.domain.member.dto.request.DeleteMemberRequestDto;
import com.example.ogiyo.domain.member.dto.request.UpdateMemberRequestDto;
import com.example.ogiyo.domain.member.dto.request.UpdatePasswordRequestDto;
import com.example.ogiyo.domain.member.dto.response.ReadMemberResponseDto;
import com.example.ogiyo.domain.member.dto.response.UpdateMemberResponseDto;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.repository.MemberRepository;
import com.example.ogiyo.common.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import com.example.ogiyo.common.config.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public ReadMemberResponseDto getMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundUserException::new);

        return ReadMemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getModifiedAt())
                .build();
    }

    @Transactional
    public UpdateMemberResponseDto updateMember(UpdateMemberRequestDto dto, Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundUserException::new);

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException();
        }

        if(dto.getName() != null && !dto.getName().isBlank()) {
            member.updateName(dto.getName());
        }
        if(dto.getEmail() != null && !dto.getEmail().isBlank()) {
            member.updateEmail(dto.getEmail());
        }

        return UpdateMemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getModifiedAt())
                .build();
    }

    @Transactional
    public void updateMemberPassword(UpdatePasswordRequestDto dto, Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundUserException::new);

        if(!passwordEncoder.matches(dto.getOldPassword(), member.getPassword())) {
            throw new InvalidPasswordException();
        }

        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());

        member.updatePassword(encodedPassword);
    }

    @Transactional
    public void deleteMember(DeleteMemberRequestDto dto, Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundUserException::new);

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException();
        }

        member.delete();
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) { return memberRepository.findByEmail(email); }

    public Optional<Member> findById(Long id) { return memberRepository.findById(id); }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean existsById(Long id) { return memberRepository.existsById(id);
    }
}
