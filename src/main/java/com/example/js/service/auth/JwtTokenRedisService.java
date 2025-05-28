package com.example.js.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtTokenRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Refresh Token 저장
     */
    public void saveRefreshToken(String userId, String refreshToken, long expirationMillis) {
        String key = getRefreshTokenKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(expirationMillis));
    }

    /**
     * Refresh Token 조회
     */
    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(getRefreshTokenKey(userId));
    }

    /**
     * Refresh Token 삭제 (로그아웃 시)
     */
    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(getRefreshTokenKey(userId));
    }

    private String getRefreshTokenKey(String userId) {
        return "RT:" + userId;
    }

}