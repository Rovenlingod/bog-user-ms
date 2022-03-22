package com.example.boguserms.utulities;

import com.example.boguserms.dto.UserDetailsDTO;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

public class CustomUtils {

    public static Optional<UserDetailsDTO> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsDTO) {
            return Optional.of((UserDetailsDTO) principal);
        } else {
            return Optional.empty();
        }
    }
}
