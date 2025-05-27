package com.example.js.service.user;

import com.example.js.dto.user.UserResponse;
import com.example.js.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public UserResponse selectUserById(UserResponse user) {
        return userMapper.selectUserById(user);
    }

    public UserResponse findByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }

    public List<String> findRolesByUserId(String userId) {
        return userMapper.findRolesByUserId(userId);
    }
}
