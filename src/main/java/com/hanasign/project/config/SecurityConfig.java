package com.hanasign.project.config;

import com.hanasign.project.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 * - JWT 기반 보안 설정, 인증 필터 연결
 */

@Configuration
@RequiredArgsConstructor

@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    /**
     * 비밀번호 암호화용 PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 생성
     */
    @Bean
    public AuthenticationManager authManger(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService) // 사용자 정의 UserDetailsService 설정
                .passwordEncoder(passwordEncoder()) // 비밀번호 인코더 설정
                .and()
                .build();
    }

    /**
     * SecurityFilterChain 설정
     * - 세션 미사용 (stateless)
     * - /api/auth/**는 인증 없이 허용
     * - 나머지는 인증 필요
     * - JWT 필터 추가
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 변경된 방식
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT를 사용한 인증,인과로 하기떄문에 세션 사용 X
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 로그인 API는 인증 없이 접근 가능
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 URL 설정
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // 유효값 체크 및 해당 사용자 관련 JWT 인증 필터 추가
        return http.build();
    }
}