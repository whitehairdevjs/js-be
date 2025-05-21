package com.example.js.controller;

import com.example.js.domain.User;
import com.example.js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public User selectUserById(@RequestBody User user) {
        return userService.selectUserById(user);
    }

}
