package com.example.boguserms.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
