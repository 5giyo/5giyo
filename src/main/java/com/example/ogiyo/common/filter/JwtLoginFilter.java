package com.example.ogiyo.common.filter;

import com.auth0.jwt.JWT;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.common.exception.ErrorCode;
import com.example.ogiyo.common.exception.ErrorMessage;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtLoginFilter implements Filter {
    private final static String[] WHITE_LIST = {
            "/auth/signup",
            "/auth/login",
    };

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUri = request.getRequestURI();

        if(!isWhiteList(requestUri)){
            String jwt = request.getHeader(JwtProperties.HEADER_STRING);
            String token = jwt.replace(JwtProperties.TOKEN_PREFIX, "");

            Date ExpiresDate = JWT.decode(token).getExpiresAt();

            if(jwt.isEmpty() || ExpiresDate.before(new Date())){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorMessage(ErrorCode.NOT_LOGIN, HttpStatus.UNAUTHORIZED)));
                return;
            }

            Long memberId = jwtUtil.extractMemberId(jwt);
            if(!memberService.existsById(memberId)){
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorMessage(ErrorCode.NOT_FOUND_MEMBER, HttpStatus.UNAUTHORIZED)));
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public boolean isWhiteList(String requestUri){return PatternMatchUtils.simpleMatch(WHITE_LIST, requestUri);}
}
