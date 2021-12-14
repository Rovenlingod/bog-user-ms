package com.example.boguserms.dto;

import lombok.Data;

@Data
public class LoginSearchResponseDTO {
    private String id;
    private String login;
    private String encryptedPassword;
    private String email;
    private String firstName;
    private String lastName;
}
