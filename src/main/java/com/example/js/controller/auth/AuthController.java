package com.example.js.controller.auth;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.js.security.JwtTokenProvider;
import com.example.js.mapper.user.UserMapper;
import com.example.js.domain.user.DomainUser;
import com.example.js.dto.auth.LoginRequest;
import com.example.js.dto.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * 1) POST /auth/login → 아이디/비밀번호 검증
 * 2) 성공 시 JwtTokenProvider.createToken() 호출 → 토큰 발급
 * 3) 클라이언트에 토큰 전달
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        // ① 인증 매니저로 사용자 검증
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUserId(), req.getPassword())
        );

        // ② DB에서 user, roles 조회
        DomainUser user = userMapper.findByUserId(req.getUserId());
        List<String> roles = userMapper.findRolesByUserId(user.getUserId());

        // ③ JWT 생성 후 응답
        String token = tokenProvider.createToken(user.getUserId(), roles);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
