package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO findByUserId(String userId);
    UserResponseDTO findByUserLogin(String login);
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO);
//    void deleteUser(User byUserId);
}
