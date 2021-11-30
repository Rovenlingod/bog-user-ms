package com.example.boguserms.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> exceptionHandler(ValidationException e) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("exception", e.getClass().toString());
        responseBody.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("exception", e.getClass().toString());
        responseBody.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

/*    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println(Arrays.toString(e.getSuppressed()));
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("exception", e.getClass().toString());
        responseBody.put("message", e.getMessage());
        System.out.println(322);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }*/

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> Objects.requireNonNull(x.getRejectedValue()).toString() + " " + x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
}
