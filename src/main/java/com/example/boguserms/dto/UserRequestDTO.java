package com.example.boguserms.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "- Login is mandatory")
    private String login;

    @NotBlank(message = "- Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!?])(?=\\S+$).{6,}$", message = "- Password must contain at least one letter, one digit and one symbol")
    private String password;

    @Email(message = "- String field must be a valid email address")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "- Firstname is mandatory")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "- Name must be started with uppercase")
    private String firstName;

    @NotBlank(message = "- Lastname is mandatory")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "- Name must be started with uppercase")
    private String lastName;
}
