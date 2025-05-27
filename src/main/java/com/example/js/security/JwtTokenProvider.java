package com.example.js.security;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 1) 로그인 성공 시 createToken() 으로 JWT 생성
 * 2) 요청 때마다 validateToken() 으로 유효성 체크
 * 3) getAuthentication() 으로 Authentication 객체 생성 → SecurityContext 설정
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;        // Base64 인코딩된 비밀키

    @Value("${jwt.issuer}")
    private String issuer;           // 토큰 발급자

    @Value("${jwt.expiration}")
    private long validityInMs;       // 토큰 유효기간 (ms)

    // 서명(Signing) Key 생성
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성: subject=아이디, roles 클레임 포함
    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date exp = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 기반으로 Authentication 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                getUsername(token),
                "",
                getRoles(token).stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities()
        );
    }

    // 토큰에서 subject(username) 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰에서 roles 클레임 추출
    @SuppressWarnings("unchecked")
    private List<String> getRoles(String token) {
        return (List<String>) Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles");
    }

    // 토큰 유효성 검사(만료, 서명)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
