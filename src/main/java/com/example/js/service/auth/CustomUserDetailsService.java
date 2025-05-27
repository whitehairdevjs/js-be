package com.example.js.service.auth;

import com.example.js.domain.user.DomainUser;
import com.example.js.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.example.js.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service 레이어:
 * MyBatis 매퍼를 통해 DB에서 User + Roles 조회 → Spring Security 에 UserDetails 로 전달
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        DomainUser domainUser = userService.findByUserId(userId);

        if (domainUser == null) {
            throw new UsernameNotFoundException("User not found: " + userId);
        }
        // DB에서 가져온 role 문자열을 GrantedAuthority 리스트로 변환
        List<GrantedAuthority> auths = userMapper.findRolesByUserId(domainUser.getUserId())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return User.withUsername(domainUser.getUserId())
                .password(domainUser.getPassword())
                .authorities(auths)
                .build();
    }
}
