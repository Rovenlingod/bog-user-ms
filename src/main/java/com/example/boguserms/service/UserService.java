package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.LoginSearchResponseDTO;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO findByUserId(String userId);
    LoginSearchResponseDTO findByUserLogin(String login);
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO);
//    void deleteUser(User byUserId);
}
