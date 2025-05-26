package com.example.js.mapper;

import com.example.js.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUserById() {
        User user = new User();
        user.setUserId("user006");
        User resultUser = userMapper.selectUserById(user);

        Assertions.assertNotNull(resultUser);
        Assertions.assertEquals(user.getUserId(), resultUser.getUserId());

        System.out.println("User id = " + resultUser.getUserId());
        System.out.println("User name = " + resultUser.getUserName());
        System.out.println("User ip = " + resultUser.getAccessIp());
    }
}
