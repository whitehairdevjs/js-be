package com.example.js.mapper;

import com.example.js.domain.User;

public interface UserMapper {
    User selectUserById(User user);
}