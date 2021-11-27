package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO findByUserId(String userId);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(User byUserId);
}
