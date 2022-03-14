package com.example.boguserms.security;

import com.example.boguserms.dto.LoginSearchResponseDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginSearchResponseDTO userDTO = userService.findByUserLogin(username);

        if (Objects.isNull(userDTO)) {
            throw new UsernameNotFoundException("Username:" + username + " not found");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        List<GrantedAuthority> authorities = Collections.singletonList(authority);

        return new User(username, userDTO.getEncryptedPassword(), authorities);
    }
}
