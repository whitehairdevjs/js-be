package com.example.js.mapper.user;

import com.example.js.domain.user.DomainUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUserById() {
        DomainUser user = new DomainUser();
        user.setUserId("user006");
        DomainUser resultUser = userMapper.selectUserById(user);

        Assertions.assertNotNull(resultUser);
        Assertions.assertEquals(user.getUserId(), resultUser.getUserId());

        System.out.println("User id = " + resultUser.getUserId());
        System.out.println("User name = " + resultUser.getUserName());
        System.out.println("User ip = " + resultUser.getAccessIp());
    }

    @Test
    public void testFindByUserId() {
        DomainUser resultUser = userMapper.findByUserId("user001");

        Assertions.assertNotNull(resultUser);

        System.out.println("User id = " + resultUser.getUserId());
        System.out.println("User password = " + resultUser.getPassword());
    }

    @Test
    public void testFindRolesByUserId() {
        List<String> roleCodeList = userMapper.findRolesByUserId("user200");

        System.out.println(roleCodeList);
    }
}
