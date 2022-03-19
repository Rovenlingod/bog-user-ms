package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.*;

public interface UserService {
    UserResponseDTO findByUserId(String userId);
    LoginSearchResponseDTO findByUserLogin(String login);
    UserDetailsDTO findUserDetailsByLogin(String login);
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateOauthUser(OAuthUserDTO oAuthUserDTO);
//    void deleteUser(User byUserId);
}
