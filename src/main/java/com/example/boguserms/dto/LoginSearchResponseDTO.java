package com.example.boguserms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginSearchResponseDTO {
    private String id;
    private String login;
    private String encryptedPassword;
    private String email;
    private String firstName;
    private String lastName;
}
