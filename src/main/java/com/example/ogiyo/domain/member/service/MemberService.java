package com.example.ogiyo.domain.member.service;

import com.example.ogiyo.domain.member.dto.response.ReadMemberResponseDto;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.repository.MemberRepository;
import com.example.ogiyo.common.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import com.example.ogiyo.common.config.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getModifiedAt())
                .build();
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) { return memberRepository.findByEmail(email); }

    public Optional<Member> findById(Long id) { return memberRepository.findById(id); }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
