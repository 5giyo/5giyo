package com.example.ogiyo.common.config;

import com.example.ogiyo.auth.service.AuthService;
import com.example.ogiyo.common.filter.JwtLoginFilter;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.member.service.MemberService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConcreteProxy
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Bean
    public FilterRegistrationBean<Filter> jwtFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new JwtLoginFilter(jwtUtil, memberService));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

}
