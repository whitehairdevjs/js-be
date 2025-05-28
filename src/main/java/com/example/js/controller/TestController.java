package com.example.js.controller;


import com.example.js.dto.user.UserRequest;
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

    @GetMapping("hello")
    public String hello() {
        return testService.getString();
    }

    @PostMapping("getRtoken")
    public String getRtoken(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest.getUserId());
        return jwtTokenRedisService.getRefreshToken(userRequest.getUserId());
    }
}
