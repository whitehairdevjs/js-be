package com.example.js.controller.user;

import com.example.js.dto.user.UserResponse;
import com.example.js.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/info")
    public UserResponse selectUserById(@RequestBody UserResponse user) {
        return userService.selectUserById(user);
    }

}
