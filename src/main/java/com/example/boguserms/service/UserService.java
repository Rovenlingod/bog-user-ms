package com.example.boguserms.service;

import com.example.boguserms.domain.User;

import java.util.UUID;

public interface UserService {
    User findByUserId(String userId);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(User byUserId);
}
