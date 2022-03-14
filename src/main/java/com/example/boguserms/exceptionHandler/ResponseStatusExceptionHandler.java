package com.example.boguserms.exceptionHandler;

import com.example.boguserms.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Set;

@ControllerAdvice
public class ResponseStatusExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handle(ResponseStatusException responseStatusException, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                responseStatusException.getRawStatusCode(),
                responseStatusException.getStatus().getReasonPhrase(),
                responseStatusException.getMessage(),
                ((ServletWebRequest)request).getRequest().getRequestURI());
        return new ResponseEntity<>(errorResponse, responseStatusException.getStatus());
    }
}
