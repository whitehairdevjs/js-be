package com.example.js.controller;

import com.example.js.dto.auth.TokenRequest;
import com.example.js.dto.user.UserRequest;
import com.example.js.security.JwtTokenProvider;
import com.example.js.service.TestService;
import com.example.js.service.auth.JwtTokenRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final JwtTokenRedisService jwtTokenRedisService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("hello")
    public String hello() {
        return testService.getString();
    }

    @PostMapping("getRtoken")
    public String getRtoken(@RequestBody UserRequest userRequest) {
        return jwtTokenRedisService.getRefreshToken(userRequest.getUserId());
    }

    @PostMapping("get-role")
    public String getUserRoleTest(@RequestBody TokenRequest tokenRequest) {
        System.out.println(tokenRequest.getRefreshToken());
        String result = jwtTokenProvider.getUserRole(tokenRequest.getRefreshToken());
        System.out.println(result);
        return result;
    }

}
