package com.example.boguserms.service;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.exception.InvalidUUIDException;
import com.example.boguserms.exception.NonexistentUserException;
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
            if (user.isPresent())
                return UserMapper.INSTANCE.UserToUserResponseDTO(user.get());
            else
                throw new NonexistentUserException("User was not found!");
        } catch(IllegalArgumentException illegalArgumentException) {
            throw new InvalidUUIDException("User id is invalid!");
        }
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
