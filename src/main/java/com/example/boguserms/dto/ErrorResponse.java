package com.example.boguserms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
