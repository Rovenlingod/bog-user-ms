package com.example.boguserms.controller;

import com.example.boguserms.dto.LoginSearchResponseDTO;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;

@Controller
@Validated
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        String id = userService.createUser(userRequestDTO).getUserId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(path = "/{user_id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable(value = "user_id") @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$") String userId) {
        return ResponseEntity.ok().body(userService.findByUserId(userId));
    }

    @PatchMapping
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(userRequestDTO));
    }

    @GetMapping
    public ResponseEntity<LoginSearchResponseDTO> getUserByLogin(@RequestParam(value = "login") String login) {
        return ResponseEntity.ok().body(userService.findByUserLogin(login));
    }

//    @DeleteMapping(path = "/user/{user_id}")
//    public ResponseEntity deleteUser(@PathVariable("user_id") String userId) {
//        userService.deleteUser(userService.findByUserId(UUID.fromString(userId))));
//        return ResponseEntity.ok().build();
//    }
}
