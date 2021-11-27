package com.example.boguserms.controller;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
    }

    @GetMapping(path = "/user/{user_id}")
    public ResponseEntity<UserResponseDTO> getEventById(@PathVariable(value = "user_id") String userId) {
        return ResponseEntity.ok().body(userService.findByUserId(userId));
    }

    @PatchMapping(path = "/user")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(userRequestDTO));
    }

//    @DeleteMapping(path = "/user/{user_id}")
//    public ResponseEntity deleteUser(@PathVariable("user_id") String userId) {
//        userService.deleteUser(userService.findByUserId(UUID.fromString(userId))));
//        return ResponseEntity.ok().build();
//    }
}
