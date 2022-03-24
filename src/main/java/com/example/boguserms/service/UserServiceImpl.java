package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.*;
import com.example.boguserms.exception.InvalidUUIDException;
import com.example.boguserms.exception.UserAlreadyExistsException;
import com.example.boguserms.mapper.UserMapper;
import com.example.boguserms.repository.UserRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO findByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new InvalidUUIDException("User id is invalid!");
        }
        try {
            User user = userRepository
                    .findById(UUID.fromString(userId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id = " + userId + " does not exist"));
            return userMapper.userToUserResponseDTO(user);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidUUIDException("User id is invalid!");
        }
    }

    @Override
    public LoginSearchResponseDTO findByUserLogin(String login) {
        User user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with login = " + login + " does not exist"));
        return userMapper.toLoginDTO(user);
    }

    @Override
    public UserResponseDTO createUser(@NonNull UserRequestDTO userRequestDTO) {
        if (userRepository.findByLogin(userRequestDTO.getLogin()).isPresent())
            throw new UserAlreadyExistsException("User already exists in database!");
        User user = userRepository.save(userMapper.userRequestDTOToUser(userRequestDTO));
        return userMapper.userToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.save(userMapper.userRequestDTOToUser(userRequestDTO));
        return userMapper.userToUserResponseDTO(user);
    }

    //TODO: Rewrite this method. It doesn't update oauth user properly
    @Override
    public UserResponseDTO updateOauthUser(@NonNull OAuthUserDTO oAuthUserDTO) {
        User user = userRepository.findByLogin(oAuthUserDTO.getEmail())
                .orElse(userMapper.oAuthDTOToUser(oAuthUserDTO));
        return userMapper.userToUserResponseDTO(userRepository.save(user));
    }

    @Override
    public UserDetailsDTO findUserDetailsByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with login = " + login + " does not exist"));
        return userMapper.userToUserDetailsDTO(user);
    }

}
