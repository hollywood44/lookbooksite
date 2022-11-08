package com.lbs.lookbooksite.configs;

import com.lbs.lookbooksite.dto.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService service;

    // 인증을 무시할 경로 지정(css,js,img  ->  인증 무시)
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/css/**","/js/***","/img/**");
    }

    // http 관련 인증 설정
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests() // 접근 관련 인증설정
                .antMatchers().permitAll()
                .antMatchers().hasRole("USER")
                .antMatchers().hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/member/login") // 로그인 페이지 설정
                .loginProcessingUrl("/member/login") // form의 action url 기본값은 '/login'
                .defaultSuccessUrl("/todo/list") // 로그인에 성공하면 이동할 페이지 설정
                .and()
                .logout()
                .logoutUrl("/member/logout") //post방식의 로그아웃폼 action에 있는 주소
                .logoutSuccessUrl("/member/login") // 로그아웃 성공하면 이동할 주소
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/denied") // 권한 없는 대상 접근시도 시 이동할 페이지
        ;
    }

    // 로그인시 필요한 정보 가져오는 메서드
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(service) // 로그인시 필요한 정보 가져오는 메서드
                .passwordEncoder(new BCryptPasswordEncoder()); // 설정한 passwordEncoder
    }
}