package com.example.js.mapper.user;

import com.example.js.dto.auth.UserRequest;
import com.example.js.dto.user.UserResponse;

import java.util.List;

public interface UserMapper {
    UserResponse selectUserById(UserRequest user);

    UserResponse findByUserId(String userId);

    List<String> findRolesByUserId(String userId);
}