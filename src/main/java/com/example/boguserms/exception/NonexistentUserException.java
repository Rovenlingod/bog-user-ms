package com.example.boguserms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NonexistentUserException extends RuntimeException {
    public NonexistentUserException(String message) {
        super(message);
    }
}
