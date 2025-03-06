package com.example.ogiyo.domain.member.controller;

import com.example.ogiyo.domain.member.dto.request.DeleteMemberRequestDto;
import com.example.ogiyo.domain.member.dto.request.UpdateMemberRequestDto;
import com.example.ogiyo.domain.member.dto.request.UpdatePasswordRequestDto;
import com.example.ogiyo.domain.member.dto.response.ReadMemberResponseDto;
import com.example.ogiyo.domain.member.dto.response.UpdateMemberResponseDto;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    //내 정보 조회
    @GetMapping
    public ResponseEntity<ReadMemberResponseDto> getMyInfo(
            @RequestHeader(JwtProperties.HEADER_STRING) String token
    ){
        Long userId = jwtUtil.extractMemberId(token);
        return new ResponseEntity<>(memberService.getMemberById(userId), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UpdateMemberResponseDto> updateMember(
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @Validated @RequestBody UpdateMemberRequestDto dto
    ){
        Long userId = jwtUtil.extractMemberId(token);
        return new ResponseEntity<>(memberService.updateMember(dto, userId), HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @Validated @RequestBody UpdatePasswordRequestDto dto
    ){
        Long userId = jwtUtil.extractMemberId(token);
        memberService.updateMemberPassword(dto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @Validated @RequestBody DeleteMemberRequestDto dto
    ){
        Long userId = jwtUtil.extractMemberId(token);
        memberService.deleteMember(dto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
