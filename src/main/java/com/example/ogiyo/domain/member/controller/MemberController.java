package com.example.ogiyo.domain.member.controller;

import com.example.ogiyo.domain.member.dto.response.ReadMemberResponseDto;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Long userId = jwtUtil.extractUserId(token);
        return new ResponseEntity<>(memberService.getMemberById(userId), HttpStatus.OK);
    }
}
