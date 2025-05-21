package com.example.js.service;

import com.example.js.domain.User;
import com.example.js.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectUserById(User user) {
        return userMapper.selectUserById(user);
    }
}
