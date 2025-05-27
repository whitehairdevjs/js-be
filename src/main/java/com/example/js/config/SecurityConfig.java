package com.example.js.config;

import com.example.js.security.JwtTokenProvider;
import com.example.js.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // roles 사용을 위해 추가
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint entryPoint;

    // AuthenticationManager 빈이 생성
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1) CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 2) 세션을 사용하지 않음(STATELESS)
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3) 인증 실패 시 401 에러 처리 엔트리 포인트
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPoint))

                // 4) 요청별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/docs/**").permitAll()   // /auth/** 은 모두 허용
                        .anyRequest().authenticated()              // 그 외 인증 필요
                )

                // 5) JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(
                        new JwtAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}