package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.exception.InvalidUUIDException;
import com.example.boguserms.exception.NonexistentUserException;
import com.example.boguserms.exception.UserAlreadyExistsException;
import com.example.boguserms.mapper.UserMapper;
import com.example.boguserms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO findByUserId(String userId) {
        try {
            Optional<User> user = userRepository.findById(UUID.fromString(userId));
            if (!user.isPresent())
                throw new NonexistentUserException("User was not found!");
            return UserMapper.INSTANCE.UserToUserResponseDTO(user.get());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidUUIDException("User id is invalid!");
        }
    }

    @Override
    public UserResponseDTO findByUserLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.map(UserMapper.INSTANCE::UserToUserResponseDTO).orElse(null);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (findByUserLogin(userRequestDTO.getLogin()) != null)
            throw new UserAlreadyExistsException("User already exists in database!");
        User user = userRepository.save(UserMapper.INSTANCE.UserRequestDTOToUser(userRequestDTO));
        return UserMapper.INSTANCE.UserToUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.save(UserMapper.INSTANCE.UserRequestDTOToUser(userRequestDTO));
        return UserMapper.INSTANCE.UserToUserResponseDTO(user);
    }

//    @Override
//    public void deleteUser(User user) {
//        userRepository.delete(user);
//    }
}
