package com.example.js.service.user;

import com.example.js.domain.user.DomainUser;
import com.example.js.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;



    public DomainUser selectUserById(DomainUser user) {
        return userMapper.selectUserById(user);
    }

    public DomainUser findByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }

    public List<String> findRolesByUserId(String userId) {
        return userMapper.findRolesByUserId(userId);
    }
}
