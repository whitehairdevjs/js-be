package com.example.js.controller.user;

import com.example.js.dto.user.UserRequest;
import com.example.js.dto.user.UserResponse;
import com.example.js.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
        summary = "유저 정보 조회",
        description = "userId에 해당하는 유저의 상세 정보를 반환합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
        }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/info")
    public UserResponse selectUserById(@RequestBody UserRequest user) {
        return userService.selectUserById(user);
    }

}
