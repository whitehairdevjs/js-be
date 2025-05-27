package com.example.js.mapper.user;

import com.example.js.domain.user.DomainUser;

import java.util.List;

public interface UserMapper {
    DomainUser selectUserById(DomainUser user);

    DomainUser findByUserId(String userId);

    List<String> findRolesByUserId(String userId);
}