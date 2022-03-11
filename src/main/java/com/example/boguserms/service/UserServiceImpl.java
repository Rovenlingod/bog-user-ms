package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.LoginSearchResponseDTO;
import com.example.boguserms.dto.OAuthUserDTO;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.exception.InvalidUUIDException;
import com.example.boguserms.exception.NonexistentUserException;
import com.example.boguserms.exception.UserAlreadyExistsException;
import com.example.boguserms.mapper.UserMapper;
import com.example.boguserms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO findByUserId(String userId) {
        try {
            User user = userRepository
                    .findById(UUID.fromString(userId))
                    .orElseThrow(() -> new NonexistentUserException("User with id = " + userId + " does not exist"));
            return userMapper.UserToUserResponseDTO(user);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidUUIDException("User id is invalid!");
        }
    }

    @Override
    public LoginSearchResponseDTO findByUserLogin(String login) {
        User user = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new NonexistentUserException("User with login = " + login + " does not exist"));
        return userMapper.toLoginDTO(user);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByLogin(userRequestDTO.getLogin()).isPresent())
            throw new UserAlreadyExistsException("User already exists in database!");
        User user = userRepository.save(userMapper.UserRequestDTOToUser(userRequestDTO));
        return userMapper.UserToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.save(userMapper.UserRequestDTOToUser(userRequestDTO));
        return userMapper.UserToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateOauthUser(OAuthUserDTO oAuthUserDTO) {
        User user = userRepository.findByLogin(oAuthUserDTO.getEmail())
                .orElse(userMapper.oAuthDTOToUser(oAuthUserDTO));
        return userMapper.UserToUserResponseDTO(userRepository.save(user));
    }

    //    @Override
//    public void deleteUser(User user) {
//        userRepository.delete(user);
//    }
}
